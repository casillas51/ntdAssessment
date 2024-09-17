package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.OperationRepository;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import com.ntdsoftware.homework.casillas.common.service.impl.CommonServiceImpl;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the CommonServiceImpl class.
 * This class tests the methods of CommonServiceImpl to ensure they work as expected.
 */
@Slf4j
public class CommonServiceTest implements ApplicationTest {

    /**
     * The UserRepository instance to interact with the database.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * The OperationRepository instance to interact with the database.
     */
    @Mock
    private OperationRepository operationRepository;

    /**
     * The CommonServiceImpl instance to test.
     */
    @InjectMocks
    private CommonServiceImpl commonService;

    /**
     * Tests that a user is returned when a valid user ID is provided.
     */
    @Test
    void whenGetUserById_thenUserIsReturned() {
        User user = new User().setId(1).setUsername("test");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = commonService.getUserById(1);
        assertNotNull(result);
        assertEquals(user, result);
    }

    /**
     * Tests that a UserNotFoundException is thrown when a user ID is not found.
     */
    @Test
    void whenGetUserByIdNotFound_thenUserNotFoundExceptionIsThrown() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> commonService.getUserById(1));
    }

    /**
     * Tests that a user is returned when a user is updated.
     */
    @Test
    void whenUpdateUser_thenUserIsReturned() {
        User user = new User().setId(1).setUsername("test");
        when(userRepository.save(user)).thenReturn(user);

        User result = commonService.updateUser(user);
        assertNotNull(result);
        assertEquals(user, result);
    }

    /**
     * Tests that an operation is returned when a valid operation type is provided.
     */
    @Test
    void whenGetOperationByType_thenOperationIsReturned() {
        Operation operation = new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION);
        when(operationRepository.findByOperationType(OperationTypeEnum.ADDITION)).thenReturn(Optional.of(operation));

        Operation result = commonService.getOperationByType(OperationTypeEnum.ADDITION);
        assertNotNull(result);
        assertEquals(operation, result);
    }

    /**
     * Tests that an OperationTypeNotFoundException is thrown when an operation type is not found.
     */
    @Test
    void whenGetOperationByTypeNotFound_thenOperationTypeNotFoundExceptionIsThrown() {
        when(operationRepository.findByOperationType(OperationTypeEnum.ADDITION)).thenReturn(Optional.empty());
        assertThrows(OperationTypeNotFoundException.class, () -> commonService.getOperationByType(OperationTypeEnum.ADDITION));
    }
}