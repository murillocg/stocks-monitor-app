package com.murillocg.stocks_monitor_app.service;

import com.murillocg.stocks_monitor_app.event.StockPricesUpdated;
import org.springframework.context.ApplicationListener;

public class WalletStocksMonthlyReport implements ApplicationListener<StockPricesUpdated> {

    //Every first friday 5pm in the month
    public void stocksMonthlyRepost() {
        //for each stock in the stock table
        //look up to 30 days before today and calculate the earnings/losses between today's prices and 30 days before
    }

    @Override
    public void onApplicationEvent(StockPricesUpdated event) {

    }

}
