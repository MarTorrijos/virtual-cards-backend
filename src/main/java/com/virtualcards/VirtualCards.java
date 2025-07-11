package com.virtualcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VirtualCards {

	public static void main(String[] args) {
		SpringApplication.run(VirtualCards.class, args);
	}

}