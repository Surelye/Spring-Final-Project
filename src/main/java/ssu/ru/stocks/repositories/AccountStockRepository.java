package ssu.ru.stocks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ssu.ru.stocks.models.AccountStock;
import ssu.ru.stocks.models.AccountStockId;

import java.util.List;
import java.util.Optional;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
    List<AccountStock> findAllByAccountId(int accountId);

    Optional<AccountStock> findByAccountIdAndStockId(int accountId, int stockId);

    @Modifying
    @Query("""
            UPDATE AccountStock a_s
            SET a_s.quantityHeld = a_s.quantityHeld + :quantity
            WHERE a_s.accountId = :accId AND a_s.stockId = :stockId""")
    void updateStockQuantity(int accId, int stockId, int quantity);
}
