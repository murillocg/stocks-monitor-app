package com.murillocg.stocks_monitor_app;

import com.murillocg.stocks_monitor_app.service.StockPricesDailyUpdaterJob;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class AppInitializerTester implements CommandLineRunner {

    private final StockPricesDailyUpdaterJob stockPricesDailyUpdaterJob;

    public AppInitializerTester(StockPricesDailyUpdaterJob stockPricesDailyUpdaterJob) {
        this.stockPricesDailyUpdaterJob = stockPricesDailyUpdaterJob;
    }

    @Override
    public void run(String... args) throws Exception {
        stockPricesDailyUpdaterJob.updateAllStockPrices();
    }

}
