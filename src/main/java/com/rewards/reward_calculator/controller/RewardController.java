package com.rewards.reward_calculator.controller;

import com.rewards.reward_calculator.exception.NoCustomerFoundException;
import com.rewards.reward_calculator.model.RewardResponse;
import com.rewards.reward_calculator.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {
    @Autowired
    private RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /*GET API to calculate Reward points for input cId*/
    @GetMapping("/calculate/{cId}")
    public ResponseEntity<List<RewardResponse>> calculateRewardsForInputData(@PathVariable String cId) {
        boolean customerExists = rewardService.customerExists(cId);
        if (!customerExists) {
            throw new NoCustomerFoundException("Customer ID '" + cId + "' does not exist in records");
        }
        else {
            List<RewardResponse> rewardResponses = rewardService.calculateRewards(cId);
            return ResponseEntity.ok(rewardResponses);
        }
    }

    /*Fallback method if cId is empty*/
    @GetMapping({"/calculate","/calculate/"})
    public ResponseEntity<String> fallBackMethod(){
        throw new NoCustomerFoundException("Customer ID must not be null or empty");
    }
}
