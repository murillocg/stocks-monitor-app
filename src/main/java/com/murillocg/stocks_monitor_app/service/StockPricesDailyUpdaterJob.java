package com.murillocg.stocks_monitor_app.service;

import com.murillocg.stocks_monitor_app.entity.StockPriceHistory;
import com.murillocg.stocks_monitor_app.entity.StockPriceHistoryId;
import com.murillocg.stocks_monitor_app.event.StockPricesUpdated;
import com.murillocg.stocks_monitor_app.model.StockQuote;
import com.murillocg.stocks_monitor_app.repository.StockPriceHistoryRepository;
import com.murillocg.stocks_monitor_app.repository.WalletStocksRepository;
import com.murillocg.stocks_monitor_app.repository.WatchlistStocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockPricesDailyUpdaterJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPricesDailyUpdaterJob.class);

    private final WalletStocksRepository walletStocksRepository;

    private final WatchlistStocksRepository watchlistStocksRepository;

    private final StockQuoteClient stockQuoteClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final StockPriceHistoryRepository stockPriceHistoryRepository;

    public StockPricesDailyUpdaterJob(WalletStocksRepository walletStocksRepository, WatchlistStocksRepository watchlistStocksRepository,
                                      StockQuoteClient stockQuoteClient, ApplicationEventPublisher applicationEventPublisher,
                                      StockPriceHistoryRepository stockPriceHistoryRepository) {
        this.walletStocksRepository = walletStocksRepository;
        this.watchlistStocksRepository = watchlistStocksRepository;
        this.stockQuoteClient = stockQuoteClient;
        this.applicationEventPublisher = applicationEventPublisher;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
    }

    public void updateAllStockPrices() {
        LocalDateTime now = LocalDateTime.now();
        Set<String> myStocks = walletStocksRepository.getAllStocks();
        Set<String> watchlistStocks = watchlistStocksRepository.getAllStocks();

        var allStocks = Set.of(myStocks, watchlistStocks).stream().flatMap(Set::stream).collect(Collectors.toSet());

        for (String stock : allStocks) {
            LOGGER.info("Requesting the stock quote for the stock {}", stock);

            StockQuote stockQuote = stockQuoteClient.getQuote(stock);

            //Add the stock price in the stock price history table
            var id = new StockPriceHistoryId(stock, LocalDate.now());
            StockPriceHistory stockPriceHistory = new StockPriceHistory(id, stockQuote.price(), "BRL");
            stockPriceHistoryRepository.save(stockPriceHistory);

            //TODO: Update the highest price
        }

        applicationEventPublisher.publishEvent(new StockPricesUpdated(now));
    }
}
