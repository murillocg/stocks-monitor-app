package com.murillocg.stocks_monitor_app.service;

import com.murillocg.stocks_monitor_app.event.StockPricesUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class StocksInBigFallDailyReport implements ApplicationListener<StockPricesUpdated> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StocksInBigFallDailyReport.class);

    //Every day at 5pm
    @Override
    public void onApplicationEvent(StockPricesUpdated event) {
        LOGGER.info("Received StockPricesUpdated event: {}", event);

        //table with the following columns: stock symbol, current price, highest price, last update

        //for each stock in this table
        //is the current price 15% lower than the highest price? -> send alert
        //is the current price greater than the highest price? -> update the highest price
    }

}