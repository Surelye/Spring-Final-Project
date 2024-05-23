package ssu.ru.stocks.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.repositories.AccountsRepository;

import java.util.Optional;

@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountByUsername(String username) {
        return accountsRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountByUsernameEagerly(String username) {
        Optional<Account> account = accountsRepository.findByUsername(username);
        account.ifPresent(acc -> Hibernate.initialize(acc.getStocks()));
        return account;
    }
}
