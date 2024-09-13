package com.murillocg.stocks_monitor_app;

import com.murillocg.stocks_monitor_app.model.BrapiModels;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock-price-client", url = "https://brapi.dev/")
public interface StockPriceClient {

    @GetMapping(value = "/api/quote/{stockName}", headers = {"Authorization=Bearer 3qyemYd8DskkvdfJKy7ePX"} )
    BrapiModels.Response getQuote(@PathVariable String stockName);

}
