package com.rewards.reward_calculator;

import org.springframework.boot.SpringApplication;

public class TestRewardCalculatorApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(RewardCalculatorApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
