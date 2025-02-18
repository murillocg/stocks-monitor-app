package com.murillocg.stocks_monitor_app.service;

import com.murillocg.stocks_monitor_app.entity.StockKeyPrices;
import com.murillocg.stocks_monitor_app.entity.StockPriceHistory;
import com.murillocg.stocks_monitor_app.entity.StockPriceHistoryId;
import com.murillocg.stocks_monitor_app.event.StockPricesUpdated;
import com.murillocg.stocks_monitor_app.model.StockQuote;
import com.murillocg.stocks_monitor_app.repository.StockKeyPricesRepository;
import com.murillocg.stocks_monitor_app.repository.StockPriceHistoryRepository;
import com.murillocg.stocks_monitor_app.repository.WalletStocksRepository;
import com.murillocg.stocks_monitor_app.repository.WatchlistStocksRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StockPricesDailyUpdaterJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPricesDailyUpdaterJob.class);

    private final WalletStocksRepository walletStocksRepository;

    private final WatchlistStocksRepository watchlistStocksRepository;

    private final StockQuoteClient stockQuoteClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final StockPriceHistoryRepository stockPriceHistoryRepository;

    private final StockKeyPricesRepository stockKeyPricesRepository;

    public StockPricesDailyUpdaterJob(WalletStocksRepository walletStocksRepository,
            WatchlistStocksRepository watchlistStocksRepository, StockQuoteClient stockQuoteClient,
            ApplicationEventPublisher applicationEventPublisher,
            StockPriceHistoryRepository stockPriceHistoryRepository,
            StockKeyPricesRepository stockKeyPricesRepository) {
        this.walletStocksRepository = walletStocksRepository;
        this.watchlistStocksRepository = watchlistStocksRepository;
        this.stockQuoteClient = stockQuoteClient;
        this.applicationEventPublisher = applicationEventPublisher;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
        this.stockKeyPricesRepository = stockKeyPricesRepository;
    }

    // Scheduler for Monday to Friday at 5 PM
    @Scheduled(cron = "0 0 17 * * MON-FRI")
    public void updateAllStockPrices() {
        LocalDateTime now = LocalDateTime.now();
        Set<String> myStocks = walletStocksRepository.getAllStocks();
        Set<String> watchlistStocks = watchlistStocksRepository.getAllStocks();

        var allStocks = Set.of(myStocks, watchlistStocks).stream().flatMap(Set::stream).collect(Collectors.toSet());

        for (String stock : allStocks) {
            LOGGER.info("Requesting the stock quote for the stock {}", stock);

            StockQuote stockQuote = stockQuoteClient.getQuote(stock);

            // Add the stock price in the stock price history table
            var id = new StockPriceHistoryId(stock, LocalDate.now());
            StockPriceHistory stockPriceHistory = new StockPriceHistory(id, stockQuote.price(), "BRL");
            stockPriceHistoryRepository.save(stockPriceHistory);

            // Update the highest price
            Optional<StockKeyPrices> keyPricesOpt = stockKeyPricesRepository.findById(stock);
            if (keyPricesOpt.isEmpty()) {
                var stockKeyPrices = new StockKeyPrices(stock, stockQuote.price(), 0.0, null, 999.99, null);
                keyPricesOpt = Optional.of(stockKeyPrices);
            }
            var stockKeyPrices = keyPricesOpt.get();
            updateStockKeyPrices(stockKeyPrices, stockQuote.price());

        }

        applicationEventPublisher.publishEvent(new StockPricesUpdated(now));
    }

    private void updateStockKeyPrices(StockKeyPrices existingKeyPrices, double todayPrice) {
        double newHighPrice = existingKeyPrices.highPrice();
        LocalDate newHighPriceDate = existingKeyPrices.highPriceDate();
        double newLowPrice = existingKeyPrices.lowPrice();
        LocalDate newLowPriceDate = existingKeyPrices.lowPriceDate();
        LocalDate todayDate = LocalDate.now();

        if (todayPrice > existingKeyPrices.highPrice()) {
            newHighPrice = todayPrice;
            newHighPriceDate = todayDate;
        }
        if (todayPrice < existingKeyPrices.lowPrice()) {
            newLowPrice = todayPrice;
            newLowPriceDate = todayDate;
        }

        var newKeyPrices = new StockKeyPrices(existingKeyPrices.symbol(), todayPrice, newHighPrice, newHighPriceDate,
                newLowPrice, newLowPriceDate);
        stockKeyPricesRepository.save(newKeyPrices);
    }

}
