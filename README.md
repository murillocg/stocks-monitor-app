# Stocks Monitor App

## Features to implement

### Stocks from my wallet

- [ ] Stocks that dropped over than 15% since their highest price - Check daily
- [ ] % gain/loss in the last week - Check weekly
- [ ] % gain/loss in the last month - Check monthly
- [ ] % gain/loss in the last 12 months - Check monthly

### Stocks in the Watch List

- [ ] % gain/loss in the last week
- [ ] % gain/loss in the last month
- [ ] % gain/loss in the last 3 months

## Design and Project

### Response format from BRAPI API
```json
{
    "results": [
        {
            "currency": "BRL",
            "shortName": "PETROBRAS   PN  EDJ N2",
            "longName": "Petróleo Brasileiro S.A. - Petrobras",
            "regularMarketChange": 0.23,
            "regularMarketChangePercent": 0.597,
            "regularMarketTime": "2024-09-04T16:05:00.000Z",
            "regularMarketPrice": 38.76,
            "regularMarketDayHigh": 38.92,
            "regularMarketDayRange": "38.52 - 38.92",
            "regularMarketDayLow": 38.52,
            "regularMarketVolume": 13268300,
            "regularMarketPreviousClose": 38.53,
            "regularMarketOpen": 39.62,
            "fiftyTwoWeekRange": "38.52 - 38.92",
            "fiftyTwoWeekLow": 38.52,
            "fiftyTwoWeekHigh": 38.92,
            "symbol": "PETR4",
            "priceEarnings": 6.4258880906874,
            "earningsPerShare": 6.0692163,
            "logourl": "https://s3-symbol-logo.tradingview.com/brasileiro-petrobras--big.svg"
        }
    ],
    "requestedAt": "2024-09-04T16:24:09.315Z",
    "took": "0ms"
}
```

### Structure to persist data

- WalletTable
  - stock_symbol (string)
  - highest_price (double)

- StockPrices
  - created_at (datetime)
  - stock_symbol (string)
  - regular_price (double)

### Workflow

#### Create a daily job to request all stock prices for the current day
Every weekday a job is triggered in the evening 4PM BRT
- It goes to WalletTable and request the prices/quotes for each stock in this table
- Saving the regularPrice and date time for each stock in the StockPrices
- TDB: What about the highest price? I need the regular price to compare and likely to need the date when this happened
- Publish an event saying that DailyStockPricesUpdated

#### Stocks that dropped over than 15% since their highest price - Check daily
Whenever the DailyStockPricesUpdated was triggered
- Go to WalletTable and for each stock in this table
- If the today's price is lower than the highest price in 15%, add this stock to alertList
- If there is at least one stock in the alertList, send this list by email or another way

#### % gain/loss in the last week - Check weekly
Create a weekly job to runs every friday in the evening 4PM BRT
