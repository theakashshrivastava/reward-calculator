package com.rewards.reward_calculator.service;

import com.rewards.reward_calculator.model.Customer_Transaction;
import com.rewards.reward_calculator.model.RewardResponse;
import com.rewards.reward_calculator.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    void setup() {
        rewardService = new RewardService(transactionRepository);
    }

    @Test
    void transactionsWithinThreeMonths() {
        Customer_Transaction recentTransaction1 =
                new Customer_Transaction("CUST001", 120.0, LocalDate.of(2025,04,10));
        Customer_Transaction recentTransaction2 =
                new Customer_Transaction("CUST001", 120.0, LocalDate.of(2025,05,15));
        when(transactionRepository.findByCustomerId("CUST001"))
                .thenReturn(List.of(recentTransaction1, recentTransaction2));

        List<RewardResponse> responses = rewardService.calculateRewards("CUST001");
        assertEquals(1, responses.size());

        RewardResponse response = responses.get(0);
        assertEquals("CUST001", response.getCustomerId());
        assertEquals(2, response.getMonthlyPoints().size());

        int expectedPoints = calculateExpectedPoints(recentTransaction1.getAmount()) +
                calculateExpectedPoints(recentTransaction2.getAmount());

        assertEquals(expectedPoints, response.getTotalPoints());
    }
    private int calculateExpectedPoints(double amount) {
        if (amount <= 50) return 0;
        if (amount <= 100) return (int) (amount - 50);
        return 50 + (int) ((amount - 100) * 2);
    }

    @Test
    void transactionsOlderThanThreeMonths() {
        Customer_Transaction oldTransaction =
                new Customer_Transaction("CUST006",
                        225.0,
                        LocalDate.of(2024,12,22));
        when(transactionRepository.findByCustomerId(oldTransaction.getCustomerId()))
                .thenReturn(List.of(oldTransaction));

        List<RewardResponse> responses = rewardService.calculateRewards("CUST006");
        assertEquals(0, responses.size());
    }

    @Test
    public void whenCustomerExists(){
        String cId = "CUST005";
        List<Customer_Transaction> transactions =
                List.of(new Customer_Transaction(cId,120.0,LocalDate.now().minusDays(30)));
        when(transactionRepository.findByCustomerId(cId)).thenReturn(transactions);
        assertTrue(rewardService.customerExists(cId));
    }

    @Test
    public void whenCustomerDoesNotExist(){
        String cId = "CUST099";
        when(transactionRepository.findByCustomerId(cId)).thenReturn(Collections.emptyList());
        assertFalse(rewardService.customerExists(cId));
    }
}

