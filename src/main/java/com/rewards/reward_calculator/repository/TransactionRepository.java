package com.rewards.reward_calculator.repository;

import com.rewards.reward_calculator.model.Customer_Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {
    public List<Customer_Transaction> findByCustomerId(String customerId){
        List<Customer_Transaction> mockTransactions = new ArrayList<>();

        if ("CUST001".equals(customerId)) {
     mockTransactions.add(new Customer_Transaction("CUST001", 120.0, LocalDate.of(2025, 4, 10)));
     mockTransactions.add(new Customer_Transaction("CUST001", 120.0, LocalDate.of(2025, 5, 15)));
     mockTransactions.add(new Customer_Transaction("CUST001", 225.0, LocalDate.of(2024, 12, 22)));

        } else if ("CUST002".equals(customerId)) {
    mockTransactions.add(new Customer_Transaction("CUST002", 120.0, LocalDate.of(2025, 4, 17)));
    mockTransactions.add(new Customer_Transaction("CUST002", 120.0, LocalDate.of(2024, 1, 10)));

        } else if ("CUST003".equals(customerId)) {
    mockTransactions.add(new Customer_Transaction("CUST003", 120.0, LocalDate.of(2025, 3, 10)));

        } else if ("CUST004".equals(customerId)) {
    mockTransactions.add(new Customer_Transaction("CUST004", 120.0, LocalDate.of(2025, 3, 25)));

        } else if ("CUST005".equals(customerId)) {
    mockTransactions.add(new Customer_Transaction("CUST005", 120.0, LocalDate.of(2025, 3, 25)));
    mockTransactions.add(new Customer_Transaction("CUST005", 120.0, LocalDate.of(2025, 4, 27)));
        }
        else if ("CUST006".equals(customerId)) {
            mockTransactions.add(new Customer_Transaction("CUST006", 120.0, LocalDate.of(2024, 3, 25)));
            mockTransactions.add(new Customer_Transaction("CUST006", 120.0, LocalDate.of(2025, 1, 12)));
        }
    return mockTransactions;
    }
}