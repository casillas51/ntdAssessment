package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.service.IBalanceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user balances.
 * This controller provides an endpoint for depositing balances for users.
 */
@RestController
@RequestMapping("${application.api.version1.user}/balance")
@Slf4j
public class CommonBalanceRestController extends CommonController {

    /** The BalanceService instance to handle balance operations. */
    private final IBalanceService balanceService;

    /**
     * Constructs a new UserBalanceRestController with the given UserService and BalanceService.
     *
     * @param userService the UserService instance to handle user operations
     * @param balanceService the BalanceService instance to handle balance operations
     */
    public CommonBalanceRestController(IUserService userService, IBalanceService balanceService) {
        super(userService);
        this.balanceService = balanceService;
    }

    /**
     * Retrieves the balance for a user.
     *
     * @param request the HttpServletRequest containing the user information
     * @return a ResponseEntity containing the user's balance
     */
    @GetMapping
    public ResponseEntity<Double> getBalance(HttpServletRequest request) {

        int userId = getUserId(request);

        log.info("Get balance for user: {}", userId);

        Double response = balanceService.getBalance(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Deposits an amount into the balance for a user.
     *
     * @param request the HttpServletRequest containing the user information
     * @param deposit the amount to be deposited
     * @return a ResponseEntity containing the updated balance
     */
    @PutMapping
    public ResponseEntity<Double> deposit(HttpServletRequest request,
            @Valid @Min(value = 0, message = "Deposit amount must be greater than 0")
            @NotNull(message = "Deposit amount is required") double deposit) {

        int userId = getUserId(request);

        log.info("Deposit amount for user: {}", userId);

        Double response = balanceService.deposit(userId, deposit);
        return ResponseEntity.ok(response);
    }

}