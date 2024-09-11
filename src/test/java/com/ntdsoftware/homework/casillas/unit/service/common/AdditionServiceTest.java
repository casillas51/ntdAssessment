package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.request.AdditionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.impl.AdditionServiceImpl;
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
 * Unit tests for the AdditionServiceImpl class.
 * Tests the methods for performing addition operations.
 */
public class AdditionServiceTest implements ApplicationTest {

    /**
     * The OperationServiceImpl mock instance.
     */
    @Mock
    private OperationServiceImpl operationService;

    /**
     * The AdditionServiceImpl instance to test.
     */
    @InjectMocks
    private AdditionServiceImpl additionService;

    /**
     * Tests the add method to ensure it performs the addition operation correctly.
     */
    @Test
    void whenAdd_thenPerformAddition() {
        OperationResultResponse response = new OperationResultResponse().setOperationType(OperationTypeEnum.ADDITION)
                        .setCost(10.0).setBalance(100.0);
        AdditionRequest additionRequest = new AdditionRequest().setTerm1(10.0).setTerm2(5.0);

        when(operationService.performOperationBalance(anyInt(), any(OperationTypeEnum.class))).thenReturn(response);

        OperationResultResponse result = additionService.add(1, additionRequest);

        assertEquals(OperationTypeEnum.ADDITION, result.getOperationType());
        assertEquals(15.0, result.getResult());
    }

    /**
     * Tests the add method to ensure it throws an exception when null values are provided.
     */
    @Test
    void whenAddWithNullValues_thenThrowException() {
        AdditionRequest additionRequest = new AdditionRequest().setTerm1(null).setTerm2(null);
        assertThrows(ArithmeticOperationException.class, () -> additionService.add(1, additionRequest));
    }

}