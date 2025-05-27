package com.rewards.reward_calculator.controller;

import com.rewards.reward_calculator.exception.NoCustomerFoundException;
import com.rewards.reward_calculator.model.Customer_Transaction;
import com.rewards.reward_calculator.model.RewardResponse;
import com.rewards.reward_calculator.repository.TransactionRepository;
import com.rewards.reward_calculator.service.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RewardService rewardService;

    @MockitoBean
    private TransactionRepository transactionRepository;

    @Test
    public void testCalculateRewardsForValidCustomer() throws Exception {
        String cId = "CUST001";

        RewardResponse mockResponse = new RewardResponse(
                cId,
                Map.of(LocalDate.now().withDayOfMonth(1).toString(), 120),
                120
        );

        when(rewardService.customerExists(cId)).thenReturn(true);
        when(rewardService.calculateRewards(cId)).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/api/rewards/calculate/{cId}", cId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(cId))
                .andExpect(jsonPath("$[0].totalPoints").value(120));
    }

    @Test
    public void testCalculateRewardsForInvalidCustomer() throws Exception {
        String cId = "CUST999";

        when(rewardService.customerExists(cId)).thenReturn(false);

        mockMvc.perform(get("/api/rewards/calculate/{cId}", cId))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertInstanceOf(NoCustomerFoundException.class, result.getResolvedException()))
                .andExpect(result ->
                        assertEquals("Customer ID '" + cId + "' does not exist in records",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testCalculateRewardsWithNoCustomerId() throws Exception {
        mockMvc.perform(get("/api/rewards/calculate"))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertInstanceOf(NoCustomerFoundException.class, result.getResolvedException()));
    }

    @Test
    public void transactionsOlderThanThreeMonths() throws Exception {
     Customer_Transaction oldTransaction =
     new Customer_Transaction("CUST006", 225.0,
             LocalDate.of(2024, 12, 22));
            when(transactionRepository.findByCustomerId("CUST006"))
                    .thenReturn(List.of(oldTransaction));
        when(rewardService.customerExists(oldTransaction.getCustomerId())).thenReturn(true);
        when(rewardService.calculateRewards(oldTransaction.getCustomerId())).thenReturn(List.of());

     mockMvc.perform(get("/api/rewards/calculate/CUST006"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.length()").value(0));
        }
}
