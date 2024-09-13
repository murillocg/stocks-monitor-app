package com.murillocg.stocks_monitor_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DailyJobMonitor implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyJobMonitor.class);

    private static final Set<String> stocks = Set.of("BBAS3", "VAMO3", "ELET6");

    private final StockPriceClient stockPriceClient;

    public DailyJobMonitor(StockPriceClient stockPriceClient) {
        this.stockPriceClient = stockPriceClient;
    }

    //Every day 5pm
    public void listStocksBigDip() {
        //table with the following columns: stock symbol, current price, highest price, last update

        //for each stock in this table
            //is the current price 15% lower than the highest price? -> send alert
            //is the current price greater than the highest price? -> update the highest price
    }

    //Every friday 5pm
    public void stocksWeeklyRepost() {
        //% de ganho/perda das acoes da ultima semana
        //for each stock in the stock table
            //look up to 7 days before today and calculate the earnings/losses between today's prices and 7 days before

    }

    //Every first friday 5pm in the month
    public void stocksMonthlyRepost() {
        //% de ganho/perda das acoes do ultimo mes
        //for each stock in the stock table
        //look up to 30 days before today and calculate the earnings/losses between today's prices and 30 days before
    }


    public Set<StockPrice> fetchStockPrices() {
//        var stockList = String.join(",", stocks);
        var result = stockPriceClient.getQuote("BBAS3");

        LOGGER.info("Result: {}", result);

        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        fetchStockPrices();
    }


    public record StockPrice(String symbol, double price) {}

}
