package com.rewards.reward_calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class RewardCalculatorApiApplicationTests {

	@Test
	void contextLoads() {
	}
}
