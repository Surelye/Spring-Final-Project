package ssu.ru.stocks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.ru.stocks.models.AccountStock;
import ssu.ru.stocks.models.AccountStockId;

import java.util.List;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
    List<AccountStock> findAllByAccountId(int accountId);
}
