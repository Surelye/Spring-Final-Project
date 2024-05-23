package ssu.ru.stocks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.ru.stocks.models.Stock;
import ssu.ru.stocks.repositories.StocksRepository;

import java.util.List;

@Service
public class StocksService {

    private final StocksRepository stocksRepository;

    @Autowired
    public StocksService(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Transactional(readOnly = true)
    public List<Stock> getStocks() {
        return stocksRepository.findAll();
    }
}
