package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.request.MultiplicationRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.impl.MultiplicationServiceImpl;
import com.ntdsoftware.homework.casillas.common.service.impl.OperationServiceImpl;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the MultiplicationServiceImpl class.
 * Tests the methods for performing multiplication operations.
 */
public class MultiplicationServiceTest implements ApplicationTest {

    /**
     * The OperationServiceImpl mock instance.
     */
    @Mock
    private OperationServiceImpl operationService;

    /**
     * The MultiplicationServiceImpl instance to test.
     */
    @InjectMocks
    private MultiplicationServiceImpl multiplicationService;

    /**
     * Tests the multiply method to ensure it performs the multiplication operation correctly.
     */
    @Test
    void whenMultiply_thenPerformMultiplication() {
        OperationResultResponse response = new OperationResultResponse().setOperationType(OperationTypeEnum.MULTIPLICATION)
                .setCost(10.0).setBalance(100.0).setResult("50.0");
        MultiplicationRequest request = new MultiplicationRequest().setMultiplicand(10.0).setMultiplier(5.0);

        when(operationService.performOperationBalance(anyInt(), any(OperationTypeEnum.class), any(Double.class))).thenReturn(response);

        OperationResultResponse result = multiplicationService.multiply(1, request);

        assertEquals(OperationTypeEnum.MULTIPLICATION, result.getOperationType());
        assertEquals("50.0", result.getResult());
    }

    /**
     * Tests the multiply method to ensure it throws an exception when null values are provided.
     */
    @Test
    void whenMultiplyWithNullValues_thenThrowException() {
        MultiplicationRequest request = new MultiplicationRequest().setMultiplicand(null).setMultiplier(null);
        assertThrows(ArithmeticOperationException.class, () -> multiplicationService.multiply(1, request));
    }
}