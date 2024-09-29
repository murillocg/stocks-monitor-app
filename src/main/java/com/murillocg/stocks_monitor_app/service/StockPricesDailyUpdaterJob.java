package com.murillocg.stocks_monitor_app.service;

import com.murillocg.stocks_monitor_app.entity.StockPriceHistory;
import com.murillocg.stocks_monitor_app.event.StockPricesUpdated;
import com.murillocg.stocks_monitor_app.model.StockQuote;
import com.murillocg.stocks_monitor_app.repository.WalletStocksRepository;
import com.murillocg.stocks_monitor_app.repository.WatchlistStocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockPricesDailyUpdaterJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPricesDailyUpdaterJob.class);

    private final WalletStocksRepository walletStocksRepository;

    private final WatchlistStocksRepository watchlistStocksRepository;

    private final StockQuoteClient stockQuoteClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    public StockPricesDailyUpdaterJob(WalletStocksRepository walletStocksRepository, WatchlistStocksRepository watchlistStocksRepository,
                                      StockQuoteClient stockQuoteClient, ApplicationEventPublisher applicationEventPublisher) {
        this.walletStocksRepository = walletStocksRepository;
        this.watchlistStocksRepository = watchlistStocksRepository;
        this.stockQuoteClient = stockQuoteClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void updateAllStockPrices() {
        LocalDateTime now = LocalDateTime.now();
        Set<String> myStocks = walletStocksRepository.getAllStocks();
        Set<String> watchlistStocks = watchlistStocksRepository.getAllStocks();

        var allStocks = Set.of(myStocks, watchlistStocks).stream().flatMap(Set::stream).collect(Collectors.toSet());

        for (String stock : allStocks) {
            LOGGER.info("Requesting the stock quote for the stock {}", stock);

            StockQuote stockQuote = stockQuoteClient.getQuote(stock);

            StockPriceHistory stockPriceHistory = new StockPriceHistory(now.toLocalDate(), stockQuote.symbol(), stockQuote.price());

            //TODO: Save the stock price for today

        }

        applicationEventPublisher.publishEvent(new StockPricesUpdated(now));
    }
}
