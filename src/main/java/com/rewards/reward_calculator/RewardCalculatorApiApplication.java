package com.rewards.reward_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RewardCalculatorApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(RewardCalculatorApiApplication.class, args);
	}
}
