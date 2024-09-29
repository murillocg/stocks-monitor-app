package com.murillocg.stocks_monitor_app.repository;

import com.murillocg.stocks_monitor_app.entity.StockPriceHistory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockPriceHistoryRepository {

    public void save(StockPriceHistory stockPriceHistory) {

    }

    public Optional<StockPriceHistory> findByKey(Long id) {

        return Optional.empty();
    }

}
