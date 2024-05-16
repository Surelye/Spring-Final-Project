package ssu.ru.stocks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.ru.stocks.models.Account;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
