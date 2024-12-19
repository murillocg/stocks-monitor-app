package com.murillocg.stocks_monitor_app.report;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WalletStocksWeeklyReport {

    private final ReportPricesComparator reportPricesComparator;

    public WalletStocksWeeklyReport(ReportPricesComparator reportPricesComparator) {
        this.reportPricesComparator = reportPricesComparator;
    }

    // Runs every Friday at 5 PM
    @Scheduled(cron = "0 0 17 * * FRI")
    public void stocksWeeklyReport() {
        reportPricesComparator.generateReport(7); // 7 days for weekly report
    }

}
