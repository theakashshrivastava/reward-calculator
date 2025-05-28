package com.rewards.reward_calculator.model;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerTransaction {
    private String customerId;
    private Double amount;
    private LocalDate transactionDate;
}
