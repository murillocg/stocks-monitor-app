package com.murillocg.stocks_monitor_app.service;

import com.murillocg.stocks_monitor_app.model.StockQuote;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class StockQuoteClient {

    private static final String API_URL = "https://brapi.dev/api/quote/{symbol}?token=3qyemYd8DskkvdfJKy7ePX";

    public StockQuote getQuote(String symbol) {
        String url = API_URL.replace("{symbol}", symbol);

        try {
            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();

            // Send HttpRequest and get HttpResponse
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check response status code
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error while requesting the stock quote");
            }
            // Parse JSON response
            JSONObject json = new JSONObject(response.body());
            JSONObject quote = json.getJSONArray("results").getJSONObject(0);
            String quoteSymbol = quote.getString("symbol");
            double quotePrice = quote.getDouble("regularMarketPrice");

            return new StockQuote(quoteSymbol, quotePrice);
        } catch (Exception e) {
            throw new RuntimeException("Error while requesting the stock quote", e);
        }
    }
}
