package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.OperationRepository;
import com.ntdsoftware.homework.casillas.common.service.IRecordService;
import com.ntdsoftware.homework.casillas.common.service.impl.BalanceServiceImpl;
import com.ntdsoftware.homework.casillas.common.service.impl.OperationServiceImpl;
import com.ntdsoftware.homework.casillas.common.service.impl.RecordServiceImpl;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the OperationServiceImpl class.
 * Tests the methods for retrieving operations by ID and type.
 */
@Slf4j
public class OperationServiceTest implements ApplicationTest {

    /**
     * The OperationRepository mock instance.
     */
    @Mock
    private OperationRepository operationRepository;

    @Mock
    private RecordServiceImpl recordService;

    @Mock
    private BalanceServiceImpl balanceService;

    /**
     * The OperationService instance to test.
     */
    @InjectMocks
    private OperationServiceImpl operationService;

    @Test
    void whenPerformOperationBalance_thenPerformOperation() {
        when(balanceService.withdraw(anyInt(), anyInt())).thenReturn(10.0);
        when(operationRepository.findByOperationType(OperationTypeEnum.ADDITION)).thenReturn(Optional.of(new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION).setCost(10.0)));
        doNothing().when(recordService).createRecord(anyInt(), any());

        OperationResultResponse result = operationService.performOperationBalance(1, OperationTypeEnum.ADDITION, 10.0);

        assertEquals(OperationTypeEnum.ADDITION, result.getOperationType());
    }

    @Test
    void whenPerformOperationBalanceWithInvalidOperationType_thenThrowException() {
        when(operationRepository.findByOperationType(OperationTypeEnum.ADDITION)).thenReturn(Optional.empty());

        assertThrows(OperationTypeNotFoundException.class, () -> operationService.performOperationBalance(1, OperationTypeEnum.ADDITION, 10.0));
    }

}