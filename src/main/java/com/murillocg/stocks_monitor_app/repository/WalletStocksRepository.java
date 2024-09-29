package com.murillocg.stocks_monitor_app.repository;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WalletStocksRepository {

    private static final Set<String> stocks = Set.of("BBAS3", "VAMO3", "ELET6");

    public Set<String> getAllStocks() {
        return stocks;
    }

}
