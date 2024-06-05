package ssu.ru.stocks.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.models.Stock;
import ssu.ru.stocks.repositories.StocksRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Slf4j
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

    @Transactional
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
                stocksRepository.updateStockQuantity(st.getId(), st.getQuantity() - amount);
                accountsService.updateBalance(accountId, ac.getBalance() - dealPrice);
                accountStockService.saveOrUpdate(accountId, st.getId(), amount);
                log.info("Пользователь {} приобрёл {} акций компании {}", ac.getUsername(), amount, companyName);
            }
        }
    }

    @Transactional
    public void sellStocks(int accountId, List<Integer> stockIds, List<Integer> amounts) {
        Optional<Account> account = accountsService.getAccountById(accountId);
        if (account.isPresent()) {
            Account ac = account.get();
            double profit = (1 - (ac.getCommission() / 100)) *
                    IntStream.range(0, amounts.size())
                            .mapToDouble(i -> (amounts.get(i) == null) ? 0 :
                                    amounts.get(i) * ac.getStocks().get(i).getSellPrice())
                            .sum();
            for (int i = 0; i < amounts.size(); ++i) {
                if (amounts.get(i) != null) {
                    stocksRepository.updateStockQuantity(stockIds.get(i),
                            ac.getStocks().get(i).getQuantity() + amounts.get(i));
                    accountStockService.saveOrUpdate(ac.getId(), stockIds.get(i), -amounts.get(i));
                    log.info("Пользователь {} продаёт {} акций компании {}", ac.getUsername(), amounts.get(i),
                            ac.getStocks().get(i).getCompanyName());
                }
            }
            accountsService.updateBalance(accountId, ac.getBalance() + profit);
        }
    }

    public boolean isStockExchangeAvailable() {
        return true;
//        int hour = LocalTime.now(ZoneId.of("Europe/Moscow")).getHour();
//        return (10 <= hour && hour < 22);
    }
}
