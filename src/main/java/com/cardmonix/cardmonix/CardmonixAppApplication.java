package com.cardmonix.cardmonix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CardmonixAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardmonixAppApplication.class, args);
	}

}
