package com.murillocg.stocks_monitor_app.repository;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WatchlistStocksRepository {

    private static final Set<String> stocks = Set.of(
            "UNIP6",
            "MOVI3",
            "TTEN3",
            "AMBP3",
            "NEOE3",
            "BPAN4",
            "OIBR3",
            "POMO4",
            "PSSA4"
    );

    public Set<String> getAllStocks() {
        return stocks;
    }

}
