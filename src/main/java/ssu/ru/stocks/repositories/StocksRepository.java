package ssu.ru.stocks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssu.ru.stocks.models.Stock;

import java.util.Optional;

public interface StocksRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByCompanyName(String companyName);

    @Modifying
    @Query("UPDATE Stock s SET s.quantity = :amount WHERE s.id = :id")
    void updateStockQuantity(@Param("id") int id, @Param("amount") int amount);
}
