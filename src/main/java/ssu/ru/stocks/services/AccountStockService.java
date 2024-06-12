package ssu.ru.stocks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.models.AccountStock;
import ssu.ru.stocks.repositories.AccountStockRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountStockService {

    private final AccountStockRepository accountStockRepository;

    @Autowired
    public AccountStockService(AccountStockRepository accountStockRepository) {
        this.accountStockRepository = accountStockRepository;
    }

    @Transactional(readOnly = true)
    public List<AccountStock> getAllByAccountId(int accountId) {
        List<AccountStock> accountStocks = accountStockRepository.findAllByAccountId(accountId);
        accountStocks.sort(Comparator.comparingInt(AccountStock::getStockId));
        return accountStocks;
    }

    public void saveOrUpdate(int accId, int stockId, int quantity) {
        Optional<AccountStock> accountStock = accountStockRepository.findByAccountIdAndStockId(accId, stockId);
        AccountStock accSt;
        if (accountStock.isPresent()) {
            accSt = accountStock.get();
            accSt.setQuantityHeld(accSt.getQuantityHeld() + quantity);
        } else {
            accSt = new AccountStock(accId, stockId, quantity);
        }
        accountStockRepository.save(accSt);
    }
}
