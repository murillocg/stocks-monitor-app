package com.murillocg.stocks_monitor_app.report;

import com.murillocg.stocks_monitor_app.entity.StockPriceHistoryId;
import com.murillocg.stocks_monitor_app.repository.StockPriceHistoryRepository;
import com.murillocg.stocks_monitor_app.repository.WalletStocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

@Component
public class ReportPricesComparator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportPricesComparator.class);

    private final WalletStocksRepository walletStocksRepository;
    private final StockPriceHistoryRepository stockPriceHistoryRepository;

    public ReportPricesComparator(WalletStocksRepository walletStocksRepository,
                                  StockPriceHistoryRepository stockPriceHistoryRepository) {
        this.walletStocksRepository = walletStocksRepository;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
    }

    public void generateReport(int comparisonDays) {
        LOGGER.info("ReportPricesComparator triggered by Scheduler");
        var reportRecords = new HashSet<ReportRecord>();
        var myStocks = walletStocksRepository.getAllStocks();

        for (String stock : myStocks) {
            var record = buildReportRecord(stock, comparisonDays);
            reportRecords.add(record);
        }

        if (!reportRecords.isEmpty()) {
            // TODO: Build and send the email
        }

        LOGGER.info("ReportPricesComparator finished");
    }

    private ReportRecord buildReportRecord(String stock, int comparisonDays) {
        var today = LocalDate.now();
        var todayStockPrice = stockPriceHistoryRepository.findById(new StockPriceHistoryId(stock, today))
                .orElseThrow(() -> new RuntimeException("Stock price history not found for today!"));

        var comparisonDate = today.minusDays(comparisonDays);
        var oldStockPrice = stockPriceHistoryRepository.findById(new StockPriceHistoryId(stock, comparisonDate))
                .orElseThrow(() -> new RuntimeException("Stock price history not found for " + comparisonDays + " days ago!"));

        var earnings = ((todayStockPrice.price() / oldStockPrice.price()) - 1) * 100;
        return new ReportRecord(stock, oldStockPrice.price(), todayStockPrice.price(), earnings);
    }

    public record ReportRecord(String symbol, double lastPrice, double currentPrice, double earnings) {}

}
