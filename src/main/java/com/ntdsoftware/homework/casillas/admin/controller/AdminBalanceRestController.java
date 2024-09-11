package com.ntdsoftware.homework.casillas.admin.controller;

import com.ntdsoftware.homework.casillas.admin.controller.response.BalanceResponse;
import com.ntdsoftware.homework.casillas.common.service.IBalanceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user balances.
 * This controller provides endpoints for updating and depositing balances for users.
 */
@RestController
@RequestMapping("${application.api.version1.admin}/balance")
@Slf4j
public class AdminBalanceRestController {

    /**
     * The BalanceService instance to handle balance operations.
     */
    private final IBalanceService balanceService;

    /**
     * Constructs a new BalanceRestController with the given BalanceService.
     *
     * @param balanceService the BalanceService instance to handle balance operations
     */
    public AdminBalanceRestController(IBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    /**
     * Updates the balance for a user.
     *
     * @param userId the ID of the user whose balance is to be updated
     * @param balance the new balance amount
     * @return a ResponseEntity containing the updated BalanceResponse
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<BalanceResponse> updateBalance(
            @PathVariable int userId,
            @Valid @Min(value = 0, message = "New balance must be greater than 0")
            @NotNull(message = "New balance is required") double balance) {

        log.info("Update balance for user: {}", userId);
        double response = balanceService.updateBalance(userId, balance);

        return ResponseEntity.ok(new BalanceResponse().setUserId(userId).setBalance(response));
    }

    /**
     * Deposits an amount into the balance for a user.
     *
     * @param userId the ID of the user whose balance is to be deposited into
     * @param deposit the amount to be deposited
     * @return a ResponseEntity containing the updated BalanceResponse
     */
    @PutMapping("/{userId}/deposit")
    public ResponseEntity<BalanceResponse> depositBalance(
            @PathVariable int userId,
            @Valid @Min(value = 0, message = "Deposit amount must be greater than 0")
            @NotNull(message = "Deposit amount is required") double deposit) {

        log.info("Deposit balance for user: {}", userId);
        double response = balanceService.deposit(userId, deposit);

        return ResponseEntity.ok(new BalanceResponse().setUserId(userId).setBalance(response));
    }

}