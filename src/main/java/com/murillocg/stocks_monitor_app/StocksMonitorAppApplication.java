package com.murillocg.stocks_monitor_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class StocksMonitorAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksMonitorAppApplication.class, args);
	}

}
