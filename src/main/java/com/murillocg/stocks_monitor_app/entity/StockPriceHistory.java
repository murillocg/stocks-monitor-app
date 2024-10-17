package com.murillocg.stocks_monitor_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stock_price_history")
public record StockPriceHistory(
    @Id
    StockPriceHistoryId id,
    double price,
    String currency
) {
}
