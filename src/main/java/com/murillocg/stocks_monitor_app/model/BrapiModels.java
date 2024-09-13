package com.murillocg.stocks_monitor_app.model;

import java.util.List;

public class BrapiModels {

    public record Response(List<Quote> results) {}

    public record Quote(
            String currency,
            String logourl,
            String symbol,
            String shortName,
            String longName,
            double regularMarketPrice
    ) {}
}
