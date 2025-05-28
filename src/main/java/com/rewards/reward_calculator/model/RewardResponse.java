package com.rewards.reward_calculator.model;

import lombok.*;
import java.util.Map;

@Data
@AllArgsConstructor
public class RewardResponse {
    private String customerId;
    private Map<String,Integer> monthlyPoints;
    private Integer totalPoints;
}
