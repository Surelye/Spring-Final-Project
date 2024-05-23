package ssu.ru.stocks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.ru.stocks.models.Stock;

public interface StocksRepository extends JpaRepository<Stock, Integer> {
}
