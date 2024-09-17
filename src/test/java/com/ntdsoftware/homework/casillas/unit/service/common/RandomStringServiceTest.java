package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.common.service.impl.OperationServiceImpl;
import com.ntdsoftware.homework.casillas.common.service.impl.RandomStringServiceImpl;
import com.ntdsoftware.homework.casillas.common.webClient.IApiRandomClient;
import com.ntdsoftware.homework.casillas.common.webClient.response.GenerateStringResponse;
import com.ntdsoftware.homework.casillas.common.webClient.response.RandomResponse;
import com.ntdsoftware.homework.casillas.common.webClient.response.ResultResponse;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the RandomStringServiceImpl class.
 * Tests the methods for performing random string operations.
 */
public class RandomStringServiceTest implements ApplicationTest {

    /**
     * The OperationServiceImpl mock instance.
     */
    @Mock
    private OperationServiceImpl operationService;

    /**
     * The IApiRandomClient mock instance.
     */
    @Mock
    private IApiRandomClient apiRandomClient;

    /**
     * The RandomStringServiceImpl instance to test.
     */
    @InjectMocks
    private RandomStringServiceImpl randomStringService;

    /**
     * Tests the randomString method to ensure it performs the random string operation correctly.
     */
    @Test
    void whenRandomString_thenPerformRandomString() {

        RandomResponse randomResponse = new RandomResponse().setData(new String[]{"gomEwFeMkmuYWGlvSDoywdisYRYfdPEE"}).setCompletionTime("2024-09-12 23:49:34Z");
        ResultResponse resultResponse = new ResultResponse().setRandom(randomResponse);
        GenerateStringResponse generateStringResponse = new GenerateStringResponse().setId(7).setJsonrpc("2.0").setResult(resultResponse);

        OperationResultResponse response = new OperationResultResponse().setOperationType(OperationTypeEnum.RANDOM_STRING)
                .setCost(10.0).setBalance(100.0).setResult("gomEwFeMkmuYWGlvSDoywdisYRYfdPEE");

        when(operationService.performOperationBalance(anyInt(), any(OperationTypeEnum.class), anyString())).thenReturn(response);
        when(apiRandomClient.generateString()).thenReturn(generateStringResponse);

        OperationResultResponse result = randomStringService.randomString(1);

        assertEquals(OperationTypeEnum.RANDOM_STRING, result.getOperationType());
        assertEquals("gomEwFeMkmuYWGlvSDoywdisYRYfdPEE", result.getResult());
    }

    /**
     * Tests the randomString method to ensure it throws an exception when the result is null.
     */
    @Test
    void whenRandomStringResultNullResponse_thenThrowException() {

        GenerateStringResponse generateStringResponse = new GenerateStringResponse().setId(7).setJsonrpc("2.0").setResult(null);

        OperationResultResponse response = new OperationResultResponse().setOperationType(OperationTypeEnum.RANDOM_STRING)
                .setCost(10.0).setBalance(100.0);

        when(operationService.performOperationBalance(anyInt(), any(OperationTypeEnum.class), anyString())).thenReturn(response);
        when(apiRandomClient.generateString()).thenReturn(generateStringResponse);

        assertThrows(ApplicationException.class, () -> randomStringService.randomString(1));
    }
}