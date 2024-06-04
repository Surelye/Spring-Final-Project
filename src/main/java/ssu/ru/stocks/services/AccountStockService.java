package ssu.ru.stocks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.AccountStock;
import ssu.ru.stocks.repositories.AccountStockRepository;

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
        return accountStockRepository.findAllByAccountId(accountId);
    }

    public void saveOrUpdate(int accId, int stockId, int quantity) {
        Optional<AccountStock> accountStock = accountStockRepository.findByAccountIdAndStockId(accId, stockId);
        if (accountStock.isPresent()) {
            accountStockRepository.updateStockQuantity(accId, stockId, quantity);
        } else {
            AccountStock accSt = new AccountStock(accId, stockId, quantity);
            accountStockRepository.save(accSt);
        }
    }
}
