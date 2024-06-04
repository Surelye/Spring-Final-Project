package ssu.ru.stocks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssu.ru.stocks.models.Account;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    @Modifying
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.id = :id")
    void updateBalance(@Param("id") int id, @Param("balance") double balance);
}
