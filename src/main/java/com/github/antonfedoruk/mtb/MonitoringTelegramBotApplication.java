package com.github.antonfedoruk.mtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonitoringTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringTelegramBotApplication.class, args);
	}

}
