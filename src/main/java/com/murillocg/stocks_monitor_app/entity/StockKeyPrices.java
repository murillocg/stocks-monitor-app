package com.murillocg.stocks_monitor_app.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stock_key_prices")
public record StockKeyPrices(
        @Id String symbol,
        double todayPrice,
        double highPrice,
        LocalDate highPriceDate,
        double lowPrice,
        LocalDate lowPriceDate) {
}
