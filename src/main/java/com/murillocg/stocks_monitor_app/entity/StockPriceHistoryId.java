package com.murillocg.stocks_monitor_app.entity;

import java.time.LocalDate;

public record StockPriceHistoryId(
        String stockSymbol,
        LocalDate date) {
}
