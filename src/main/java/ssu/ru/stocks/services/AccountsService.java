package ssu.ru.stocks.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.repositories.AccountsRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static ssu.ru.stocks.models.Status.*;
import static ssu.ru.stocks.util.Constants.*;

@Service
@Transactional
@Slf4j
public class AccountsService {

    private final AccountsRepository accountsRepository;
    private final Random random;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, Random random) {
        this.accountsRepository = accountsRepository;
        this.random = random;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Moscow")
    public void updateCommission() {
        List<Account> accounts = accountsRepository.findAll();
        for (Account account : accounts) {
            if (account.getStatus() == STARTER) {
                accountsRepository.updateCommission(account.getId(),
                        STARTER_LB + random.nextDouble(STARTER_UB - STARTER_LB));
            } else if (account.getStatus() == PRO) {
                accountsRepository.updateCommission(account.getId(),
                        PRO_LB + random.nextDouble(PRO_UB - PRO_LB));
            } else if (account.getStatus() == PREMIUM) {
                accountsRepository.updateCommission(account.getId(),
                        PREMIUM_LB + random.nextDouble(PREMIUM_UB - PREMIUM_LB));
            }
        }
        log.info("Произшло обновление комиссионного процента");
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountById(int id) {
        return accountsRepository.findById(id);
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

    public void updateBalance(int id, double amount) {
        accountsRepository.updateBalance(id, amount);
    }
}
