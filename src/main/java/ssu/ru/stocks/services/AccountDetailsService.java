package ssu.ru.stocks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.repositories.AccountsRepository;
import ssu.ru.stocks.security.AccountDetails;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountDetailsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountsRepository.findByUsername(username);
        if (account.isEmpty()) { // Исключение будет поймано Spring
            throw new UsernameNotFoundException("Пользователь не был найден!");
        }
        return new AccountDetails(account.get());
    }
}
