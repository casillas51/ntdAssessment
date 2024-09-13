package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.request.SubtractionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.impl.OperationServiceImpl;
import com.ntdsoftware.homework.casillas.common.service.impl.SubtractionServiceImpl;
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
 * Unit tests for the SubtractionServiceImpl class.
 * Tests the methods for performing subtraction operations.
 */
public class SubtractionServiceTest implements ApplicationTest {

    /**
     * The OperationServiceImpl mock instance.
     */
    @Mock
    private OperationServiceImpl operationService;

    /**
     * The SubtractionServiceImpl instance to test.
     */
    @InjectMocks
    private SubtractionServiceImpl subtractionService;

    /**
     * Tests the subtract method to ensure it performs the subtraction operation correctly.
     */
    @Test
    void whenSubtract_thenPerformSubtraction() {
        OperationResultResponse response = new OperationResultResponse().setOperationType(OperationTypeEnum.SUBTRACTION)
                .setCost(10.0).setBalance(100.0).setResult("5.0");
        SubtractionRequest request = new SubtractionRequest().setMinuend(10.0).setSubtrahend(5.0);

        when(operationService.performOperationBalance(anyInt(), any(OperationTypeEnum.class), any(Double.class))).thenReturn(response);

        OperationResultResponse result = subtractionService.subtract(1, request);

        assertEquals(OperationTypeEnum.SUBTRACTION, result.getOperationType());
        assertEquals("5.0", result.getResult());
    }

    /**
     * Tests the subtract method to ensure it throws an exception when null values are provided.
     */
    @Test
    void whenSubtractWithNullValues_thenThrowException() {
        SubtractionRequest request = new SubtractionRequest().setMinuend(null).setSubtrahend(null);
        assertThrows(ArithmeticOperationException.class, () -> subtractionService.subtract(1, request));
    }
}
