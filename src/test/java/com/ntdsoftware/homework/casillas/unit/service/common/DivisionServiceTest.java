package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.request.DivisionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.impl.DivisionServiceImpl;
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
 * Unit tests for the DivisionServiceImpl class.
 * Tests the methods for performing division operations.
 */
public class DivisionServiceTest implements ApplicationTest {

    /**
     * The OperationServiceImpl mock instance.
     */
    @Mock
    private OperationServiceImpl operationService;

    /**
     * The DivisionServiceImpl instance to test.
     */
    @InjectMocks
    private DivisionServiceImpl divisionService;

    /**
     * Tests the divide method to ensure it performs the division operation correctly.
     */
    @Test
    void whenDivide_thenPerformDivision() {
        OperationResultResponse response = new OperationResultResponse().setOperationType(OperationTypeEnum.DIVISION)
                .setCost(10.0).setBalance(100.0).setResult("5.0");
        DivisionRequest request = new DivisionRequest().setDividend(10.0).setDivisor(2.0);

        when(operationService.performOperationBalance(anyInt(), any(OperationTypeEnum.class), any(Double.class))).thenReturn(response);

        OperationResultResponse result = divisionService.divide(1, request);

        assertEquals(OperationTypeEnum.DIVISION, result.getOperationType());
        assertEquals("5.0", result.getResult());
    }

    /**
     * Tests the divide method to ensure it throws an exception when null values are provided.
     */
    @Test
    void whenDivideWithNullValues_thenThrowException() {
        DivisionRequest request = new DivisionRequest().setDividend(null).setDivisor(null);
        assertThrows(ArithmeticOperationException.class, () -> divisionService.divide(1, request));
    }

    /**
     * Tests the divide method to ensure it throws an exception when dividing by zero.
     */
    @Test
    void whenDivideByZero_thenThrowException() {
        DivisionRequest request = new DivisionRequest().setDividend(10.0).setDivisor(0.0);
        assertThrows(ArithmeticOperationException.class, () -> divisionService.divide(1, request));
    }

}