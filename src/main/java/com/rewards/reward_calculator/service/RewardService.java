package com.rewards.reward_calculator.service;

import com.rewards.reward_calculator.exception.NoCustomerFoundException;
import com.rewards.reward_calculator.model.Customer_Transaction;
import com.rewards.reward_calculator.model.RewardResponse;
import com.rewards.reward_calculator.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RewardService {
    @Autowired
    private TransactionRepository transactionRepository;
    public RewardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    private static final int monthsToSubtract = 3;

    public List<RewardResponse> calculateRewards(String customerId) {
        List<Customer_Transaction> transactions = transactionRepository.findByCustomerId(customerId);

        Map<String, Map<String, Integer>> customerMonthlyPoints = new HashMap<>();
        LocalDate threeMonthAgo = LocalDate.now().minusMonths(monthsToSubtract);

        for (Customer_Transaction transaction : transactions) {
            if (transaction.getTransactionDate().isBefore(threeMonthAgo)) {
                continue;
            }

            int points = calculatePoints(transaction.getAmount());
            String month = transaction.getTransactionDate().withDayOfMonth(1).toString();

            customerMonthlyPoints
                    .computeIfAbsent(customerId, k -> new HashMap<>())
                    .merge(month, points, Integer::sum);
        }
        if (customerMonthlyPoints.isEmpty()) {
            throw new NoCustomerFoundException("No transactions with in 3 months were found for customerId: " + customerId);
        } else {
            List<RewardResponse> rewardResponses = new ArrayList<>();
            for (Map.Entry<String, Map<String, Integer>> entry : customerMonthlyPoints.entrySet()) {
                String cid = entry.getKey();
                Map<String, Integer> monthlyPoints = entry.getValue();
                int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

                rewardResponses.add(new RewardResponse(cid, monthlyPoints, totalPoints));
            }
            return rewardResponses;
        }
    }
    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * 2);
            points += 50;
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }

    public boolean customerExists(String cId) {
        List<Customer_Transaction>  transactions = transactionRepository.findByCustomerId(cId);
        return transactions != null && !transactions.isEmpty();
    }
}
