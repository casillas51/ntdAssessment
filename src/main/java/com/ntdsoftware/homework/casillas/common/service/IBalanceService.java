package com.ntdsoftware.homework.casillas.common.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * BalanceService defines the contract for balance management operations.
 * It includes methods for retrieving and updating the balance of a user.
 * Implementations of this interface should handle the business logic for balance management
 * and interact with the necessary repositories and services.
 */
public interface IBalanceService {

    /**
     * Retrieves the balance of the user with the given ID.
     *
     * @param userId the ID of the user to retrieve the balance for
     * @return the balance of the user
     */
    double getBalance(int userId);

    /**
     * Updates the balance of the user with the given ID.
     *
     * @param userId the ID of the user to update the balance for
     * @param amount the amount to update the balance by
     * @return the updated balance of the user
     */
    @Transactional(rollbackFor = Exception.class)
    double updateBalance(int userId, double amount);

    /**
     * Deposits the given amount into the user's account.
     *
     * @param userId the ID of the user to deposit the amount into
     * @param amount the amount to deposit
     * @return the updated balance of the user
     */
    @Transactional(rollbackFor = Exception.class)
    double deposit(int userId, double amount);

    /**
     * Withdraws the given amount from the user's account.
     *
     * @param userId the ID of the user to withdraw the amount from
     * @param amount the amount to withdraw
     * @return the updated balance of the user
     */
    @Transactional(rollbackFor = Exception.class)
    double withdraw(int userId, double amount);

    /**
     * Checks if the user with the given ID has enough balance to withdraw the given amount.
     *
     * @param userId the ID of the user to check the balance for
     * @param amount the amount to check
     * @return true if the user has enough balance
     */
    boolean hasEnoughBalance(int userId, double amount);

}
