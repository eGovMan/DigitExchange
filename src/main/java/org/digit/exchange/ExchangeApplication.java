package org.digit.exchange;

import org.digit.exchange.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
@EnableJpaRepositories("org.digit.exchange")
@ComponentScan(basePackages = { "org.digit.exchange" })
@EntityScan(basePackages ={"org.digit.exchange","org.digit.exchange.model"})   
public class ExchangeApplication {

	public static void main(String[] args) {
		System.setProperty("org.springframework.security", "DEBUG");
		SpringApplication.run(ExchangeApplication.class, args);
	}

}
