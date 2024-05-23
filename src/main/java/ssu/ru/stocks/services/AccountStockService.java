package ssu.ru.stocks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.AccountStock;
import ssu.ru.stocks.repositories.AccountStockRepository;

import java.util.List;

@Service
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
}
