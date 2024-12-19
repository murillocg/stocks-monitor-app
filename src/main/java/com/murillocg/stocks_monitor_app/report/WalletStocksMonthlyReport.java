package com.murillocg.stocks_monitor_app.report;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WalletStocksMonthlyReport {

    private final ReportPricesComparator reportPricesComparator;

    public WalletStocksMonthlyReport(ReportPricesComparator reportPricesComparator) {
        this.reportPricesComparator = reportPricesComparator;
    }

    // Runs every first Friday of the month at 5 PM
    @Scheduled(cron = "0 0 17 ? * 5#1")
    public void stocksMonthlyRepost() {
        reportPricesComparator.generateReport(30); // 30 days for monthly report
    }

}
