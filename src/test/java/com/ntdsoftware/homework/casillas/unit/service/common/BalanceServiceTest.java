package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.exception.InvalidAmountException;
import com.ntdsoftware.homework.casillas.common.exception.NotEnoughBalanceException;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import com.ntdsoftware.homework.casillas.common.service.ICommonService;
import com.ntdsoftware.homework.casillas.common.service.impl.BalanceServiceImpl;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the BalanceService class.
 * This class tests the various methods of the BalanceService class to ensure they function correctly.
 */
public class BalanceServiceTest implements ApplicationTest {

    /**
     * The UserRepository mock instance.
     */
    @Mock
    private ICommonService commonService;

    /**
     * The BalanceService instance to test.
     */
    @InjectMocks
    private BalanceServiceImpl balanceService;

    /**
     * Test getBalance method with valid user ID.
     */
    @Test
    void whenGetBalanceWithValidUserId_thenReturnBalance() {
        User user = new User();
        user.setBalance(100.0);
        when(commonService.getUserById(anyInt())).thenReturn(user);

        double balance = balanceService.getBalance(1);
        assertEquals(100.0, balance);
    }

    /**
     * Test getBalance method with invalid user ID.
     */
    @Test
    void whenGetBalanceWithInvalidUserId_thenThrowException() {
        when(commonService.getUserById(anyInt())).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> balanceService.getBalance(1));
    }

    /**
     * Test updateBalance method with valid amount.
     */
    @Test
    void whenUpdateBalanceWithValidAmount_thenReturnUpdatedBalance() {
        User user = new User();
        user.setBalance(100.0);
        when(commonService.getUserById(anyInt())).thenReturn(user);
        when(commonService.updateUser(any(User.class))).thenReturn(user);

        double updatedBalance = balanceService.updateBalance(1, 200.0);
        assertEquals(200.0, updatedBalance);
    }

    /**
     * Test updateBalance method with invalid amount.
     */
    @Test
    void whenUpdateBalanceWithInvalidAmount_thenThrowException() {
        assertThrows(InvalidAmountException.class, () -> balanceService.updateBalance(1, -100.0));
    }

    /**
     * Test deposit method with valid amount.
     */
    @Test
    void whenDepositWithValidAmount_thenReturnUpdatedBalance() {
        User user = new User();
        user.setBalance(100.0);
        when(commonService.getUserById(anyInt())).thenReturn(user);
        when(commonService.updateUser(any(User.class))).thenReturn(user);

        double updatedBalance = balanceService.deposit(1, 50.0);
        assertEquals(150.0, updatedBalance);
    }

    /**
     * Test deposit method with invalid amount.
     */
    @Test
    void whenDepositWithInvalidAmount_thenThrowException() {
        assertThrows(InvalidAmountException.class, () -> balanceService.deposit(1, -50.0));
    }

    /**
     * Test withdraw method with valid amount.
     */
    @Test
    void whenWithdrawWithValidAmount_thenReturnUpdatedBalance() {
        User user = new User();
        user.setBalance(100.0);
        when(commonService.getUserById(anyInt())).thenReturn(user);
        when(commonService.updateUser(any(User.class))).thenReturn(user);

        double updatedBalance = balanceService.withdraw(1, 50.0);
        assertEquals(50.0, updatedBalance);
    }

    /**
     * Test withdraw method with invalid amount.
     */
    @Test
    void whenWithdrawWithInvalidAmount_thenThrowException() {
        assertThrows(InvalidAmountException.class, () -> balanceService.withdraw(1, -50.0));
    }

    /**
     * Test withdraw method with not enough balance.
     */
    @Test
    void whenWithdrawWithNotEnoughBalance_thenThrowException() {
        User user = new User();
        user.setBalance(50.0);
        when(commonService.getUserById(anyInt())).thenReturn(user);

        assertThrows(NotEnoughBalanceException.class, () -> balanceService.withdraw(1, 100.0));
    }

    /**
     * Test hasEnoughBalance method with enough balance.
     */
    @Test
    void whenHasEnoughBalanceWithEnoughBalance_thenReturnTrue() {
        User user = new User();
        user.setBalance(100.0);
        when(commonService.getUserById(anyInt())).thenReturn(user);

        assertTrue(balanceService.hasEnoughBalance(1, 50.0));
    }

    /**
     * Test hasEnoughBalance method with not enough balance.
     */
    @Test
    void whenHasEnoughBalanceWithNotEnoughBalance_thenThrowException() {
        User user = new User();
        user.setBalance(50.0);
        when(commonService.getUserById(anyInt())).thenReturn(user);

        assertThrows(NotEnoughBalanceException.class, () -> balanceService.hasEnoughBalance(1, 100.0));
    }
}