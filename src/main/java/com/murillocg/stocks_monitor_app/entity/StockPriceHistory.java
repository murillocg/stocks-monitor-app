package com.murillocg.stocks_monitor_app.entity;

import java.time.LocalDate;

public record StockPriceHistory(LocalDate date, String symbol, double price) {
}
