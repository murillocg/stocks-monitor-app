package com.murillocg.stocks_monitor_app.repository;

import com.murillocg.stocks_monitor_app.entity.StockKeyPrices;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockKeyPricesRepository extends MongoRepository<StockKeyPrices, String> {


}
