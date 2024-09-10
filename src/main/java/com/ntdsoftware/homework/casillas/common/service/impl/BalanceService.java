package com.ntdsoftware.homework.casillas.common.service.impl;

import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.exception.InvalidAmountException;
import com.ntdsoftware.homework.casillas.common.exception.NotEnoughBalanceException;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import com.ntdsoftware.homework.casillas.common.service.IBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * BalanceService provides the implementation of the IBalanceService interface.
 * It handles the business logic for managing user balances, including retrieving,
 * updating, depositing, and withdrawing balances.
 * This service interacts with the UserRepository to perform database operations.
 *
 * @see IBalanceService
 * @see UserRepository
 */
@Service
@Slf4j
public class BalanceService implements IBalanceService {

    /**
     * The UserRepository instance to interact with the database.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new BalanceService with the given UserRepository.
     *
     * @param userRepository the UserRepository instance to interact with the database
     */
    public BalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public double getBalance(int userId) {

        log.info("Get balance for user with ID: {}", userId);

        User user = getUser(userId);
        return user.getBalance();
    }

    @Override
    public double updateBalance(int userId, double amount) {

        validateAmount(amount);
        log.info("Update balance for user with ID: {} by amount: {}", userId, amount);

        User user = getUser(userId);
        user.setBalance(amount);
        user = userRepository.save(user);

        log.info("Balance updated successfully");
        return user.getBalance();
    }

    @Override
    public double deposit(int userId, double amount) {

        validateAmount(amount);
        log.info("Deposit amount: {} for user with ID: {}", amount, userId);

        User user = getUser(userId);
        user.setBalance(user.getBalance() + amount);
        user = userRepository.save(user);

        log.info("Amount deposited successfully");
        return user.getBalance();
    }

    @Override
    public double withdraw(int userId, double amount) {

        log.info("Withdraw amount: {} for user with ID: {}", amount, userId);

        User user = verifyEnoughBalance(userId, amount);
        user.setBalance(user.getBalance() - amount);
        user = userRepository.save(user);

        log.info("Amount withdrawn successfully");
        return user.getBalance();
    }

    @Override
    public boolean hasEnoughBalance(int userId, double amount) {

        log.info("Check if user with ID: {} has enough balance for amount: {}", userId, amount);

        verifyEnoughBalance(userId, amount);
        return true;
    }

    /**
     * Verifies if the user with the given ID has enough balance to withdraw the given amount.
     *
     * @param userId the ID of the user to verify the balance for
     * @param amount the amount to verify
     * @return the User instance if the user has enough balance
     * @throws NotEnoughBalanceException if the user does not have enough balance
     */
    private User verifyEnoughBalance(int userId, double amount) {

        validateAmount(amount);
        User user = getUser(userId);

        if (user.getBalance() >= amount) {
            log.info("User has enough balance");
            return user;
        }

        log.info("User does not have enough balance");
        throw new NotEnoughBalanceException();
    }

    /**
     * Retrieves the User instance with the given ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User instance
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    private User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
    }

    /**
     * Validates the given amount to ensure it is not negative.
     *
     * @param amount the amount to validate
     * @throws InvalidAmountException if the amount is negative
     */
    private void validateAmount(double amount) {
        if (amount < 0) {
            throw new InvalidAmountException();
        }
    }
}
