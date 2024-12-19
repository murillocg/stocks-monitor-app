package com.murillocg.stocks_monitor_app.service;

import org.springframework.stereotype.Component;

@Component
public class EarningsCalculator {

    public double calculateInPercentage(double newPrice, double oldPrice) {
        return ((newPrice / oldPrice) - 1) * 100;
    }

}
