package com.murillocg.stocks_monitor_app.service;

import com.murillocg.stocks_monitor_app.entity.StockPriceHistory;
import com.murillocg.stocks_monitor_app.repository.StockPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockPriceHistoryService {

    private final StockPriceHistoryRepository stockPriceHistoryRepository;

    public StockPriceHistoryService(StockPriceHistoryRepository stockPriceHistoryRepository) {
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
    }

    public Optional<StockPriceHistory> findByKey(Long id) {

        return Optional.empty();
    }

}
