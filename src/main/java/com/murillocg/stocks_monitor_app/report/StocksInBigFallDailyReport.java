package com.murillocg.stocks_monitor_app.report;

import com.murillocg.stocks_monitor_app.event.StockPricesUpdated;
import com.murillocg.stocks_monitor_app.repository.StockKeyPricesRepository;
import com.murillocg.stocks_monitor_app.repository.WalletStocksRepository;
import com.murillocg.stocks_monitor_app.service.EarningsCalculator;
import java.time.LocalDate;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class StocksInBigFallDailyReport implements ApplicationListener<StockPricesUpdated> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StocksInBigFallDailyReport.class);

    private static final double BIG_FALL_THRESHOLD_IN_PERCENTAGE = -15;

    private final WalletStocksRepository walletStocksRepository;

    private final StockKeyPricesRepository stockKeyPricesRepository;

    private final EarningsCalculator earningsCalculator;

    public StocksInBigFallDailyReport(WalletStocksRepository walletStocksRepository,
            StockKeyPricesRepository stockKeyPricesRepository, EarningsCalculator earningsCalculator) {
        this.walletStocksRepository = walletStocksRepository;
        this.stockKeyPricesRepository = stockKeyPricesRepository;
        this.earningsCalculator = earningsCalculator;
    }

    // Every day at 5pm
    @Override
    public void onApplicationEvent(StockPricesUpdated event) {
        LOGGER.info("StocksInBigFallDailyReport triggered by StockPricesUpdated event: {}", event);

        var reportRecords = new HashSet<ReportRecord>();
        var myStocks = walletStocksRepository.getAllStocks();
        for (String stock : myStocks) {
            var prices = stockKeyPricesRepository.findById(stock).orElseThrow(
                                                                              () -> new RuntimeException(
                                                                                      "Stock not found in the stockKeyPriceRepository: "
                                                                                              + stock));

            if (prices.todayPrice() < prices.highPrice()) {
                double earnings = earningsCalculator.calculateInPercentage(prices.todayPrice(), prices.highPrice());
                if (earnings < BIG_FALL_THRESHOLD_IN_PERCENTAGE) {
                    var record = new ReportRecord(stock, prices.highPrice(), prices.highPriceDate(),
                            prices.todayPrice(), earnings);
                    reportRecords.add(record);
                }
            }
        }
        if (!reportRecords.isEmpty()) {
            // TODO: Build and send the email
        }
        LOGGER.info("StocksInBigFallDailyReport finished");
    }

    record ReportRecord(String symbol, double highPrice, LocalDate highPriceDate, double currentPrice,
            double earnings) {
    }

}