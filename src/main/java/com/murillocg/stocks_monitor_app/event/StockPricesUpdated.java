package com.murillocg.stocks_monitor_app.event;

import org.springframework.context.ApplicationEvent;

public class StockPricesUpdated extends ApplicationEvent {

    public StockPricesUpdated(Object source) {
        super(source);
    }

}
