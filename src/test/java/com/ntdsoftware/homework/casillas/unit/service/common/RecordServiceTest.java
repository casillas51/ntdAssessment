package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.repository.RecordRepository;
import com.ntdsoftware.homework.casillas.common.service.ICommonService;
import com.ntdsoftware.homework.casillas.common.service.impl.RecordServiceImpl;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the RecordServiceImpl class.
 * This class tests the methods of RecordServiceImpl to ensure they work as expected.
 */
public class RecordServiceTest implements ApplicationTest {

    /**
     * The RecordRepository instance to interact with the database.
     */
    @Mock
    private RecordRepository recordRepository;

    /**
     * The ICommonService instance to perform common operations.
     */
    @Mock
    private ICommonService commonService;

    /**
     * The RecordServiceImpl instance to test.
     */
    @InjectMocks
    private RecordServiceImpl recordService;

    /**
     * Tests that a record is created when valid user and operation details are provided.
     */
    @Test
    void whenCreateRecord_thenRecordIsCreated() {
        User user = new User().setId(1).setUsername("test");
        Operation operation = new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION);
        OperationResultResponse resultResponse = new OperationResultResponse().setDoubleResult(10.0)
                .setBalance(100.00).setCost(10.00).setOperationType(OperationTypeEnum.ADDITION);

        when(commonService.getUserById(1)).thenReturn(user);
        when(commonService.getOperationByType(OperationTypeEnum.ADDITION)).thenReturn(operation);

        assertDoesNotThrow(() -> recordService.createRecord(1, resultResponse));
    }
}