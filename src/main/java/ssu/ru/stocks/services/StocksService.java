package ssu.ru.stocks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.models.Stock;
import ssu.ru.stocks.repositories.AccountsRepository;
import ssu.ru.stocks.repositories.StocksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StocksService {

    private final StocksRepository stocksRepository;
    private final AccountsService accountsService;
    private final AccountStockService accountStockService;

    @Autowired
    public StocksService(StocksRepository stocksRepository, AccountsService accountsService,
                         AccountStockService accountStockService) {
        this.stocksRepository = stocksRepository;
        this.accountsService = accountsService;
        this.accountStockService = accountStockService;
    }

    @Transactional(readOnly = true)
    public List<Stock> getStocks() {
        return stocksRepository.findAll();
    }

    public void purchaseStocks(int accountId, String companyName, int amount) {
        Optional<Account> account = accountsService.getAccountById(accountId);
        if (account.isPresent()) {
            Account ac = account.get();
            Optional<Stock> stock = stocksRepository.findByCompanyName(companyName);
            if (stock.isPresent()) {
                Stock st = stock.get();
                double dealPrice = st.getPurchasePrice() * amount * (1 + (ac.getCommission() / 100));
                if (ac.getBalance() < dealPrice || st.getQuantity() < amount) {
                    return;
                }
                stocksRepository.updateStockQuantity(st.getId(), amount);
                accountsService.updateBalance(accountId, ac.getBalance() - dealPrice);
                accountStockService.saveOrUpdate(accountId, st.getId(), amount);
            }
        }
    }
}
