package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.request.SquareRootRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.impl.OperationServiceImpl;
import com.ntdsoftware.homework.casillas.common.service.impl.SquareRootServiceImpl;
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
 * Unit tests for the SquareRootServiceImpl class.
 * Tests the methods for performing square root operations.
 */
public class SquareRootServiceTest implements ApplicationTest {

    /**
     * The OperationServiceImpl mock instance.
     */
    @Mock
    private OperationServiceImpl operationService;

    /**
     * The SquareRootServiceImpl instance to test.
     */
    @InjectMocks
    private SquareRootServiceImpl squareRootService;

    /**
     * Tests the squareRoot method to ensure it performs the square root operation correctly.
     */
    @Test
    void whenSquareRoot_thenPerformSquareRoot() {
        OperationResultResponse response = new OperationResultResponse().setOperationType(OperationTypeEnum.SQUARE_ROOT)
                .setCost(10.0).setBalance(100.0);
        SquareRootRequest request = new SquareRootRequest().setRadicand(25.0);

        when(operationService.performOperationBalance(anyInt(), any(OperationTypeEnum.class))).thenReturn(response);

        OperationResultResponse result = squareRootService.squareRoot(1, request);

        assertEquals(OperationTypeEnum.SQUARE_ROOT, result.getOperationType());
        assertEquals(5.0, result.getResult());
    }

    /**
     * Tests the squareRoot method to ensure it throws an exception when null values are provided.
     */
    @Test
    void whenSquareRootWithNullValues_thenThrowException() {
        SquareRootRequest request = new SquareRootRequest().setRadicand(null);
        assertThrows(ArithmeticOperationException.class, () -> squareRootService.squareRoot(1, request));
    }

    @Test
    void whenSquareRootWithNegativeRadicand_thenThrowException() {
        SquareRootRequest request = new SquareRootRequest().setRadicand(-1.0);
        assertThrows(ArithmeticOperationException.class, () -> squareRootService.squareRoot(1, request));
    }
}