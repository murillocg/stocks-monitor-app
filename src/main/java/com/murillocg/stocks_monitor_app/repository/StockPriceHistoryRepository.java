package com.murillocg.stocks_monitor_app.repository;

import com.murillocg.stocks_monitor_app.entity.StockPriceHistory;
import com.murillocg.stocks_monitor_app.entity.StockPriceHistoryId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceHistoryRepository extends MongoRepository<StockPriceHistory, StockPriceHistoryId> {

}
