package ssu.ru.stocks.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.models.Status;
import ssu.ru.stocks.repositories.AccountsRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public double getRandomDoubleInRange(double lb, double ub) {
        return lb + random.nextDouble(ub - lb);
    }

    public void updateCommissionEach(Account account) {
        for (Status status : Status.values()) {
            if (account.getStatus() == status) {
                accountsRepository.updateCommission(account.getId(),
                        getRandomDoubleInRange(status.getLowerBound(), status.getUpperBound()));
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Moscow")
    public void updateCommission() {
        List<Account> accounts = accountsRepository.findAll();
        for (Account account : accounts) {
            updateCommissionEach(account);
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
