package com.ntdsoftware.homework.casillas.unit.service.common;

import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.OperationRepository;
import com.ntdsoftware.homework.casillas.common.service.impl.OperationServiceImpl;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
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

    /**
     * The OperationService instance to test.
     */
    @InjectMocks
    private OperationServiceImpl operationService;

    /**
     * Test getOperationById method with a valid ID.
     * Expects the operation to be returned.
     */
    @Test
    void whenGetOperationByIdWithValidId_thenReturnOperation() {
        Operation operation = new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION).setCost(10.0);
        when(operationRepository.findById(anyInt())).thenReturn(Optional.of(operation));

        Operation result = operationService.getOperationById(1);

        assertEquals(1, result.getId());

        log.info("Operation: [{}] - {} - {}", result.hashCode(), result.toString(), result.equals(operation));
    }

    /**
     * Test getOperationById method with an invalid ID.
     * Expects an OperationTypeNotFoundException to be thrown.
     */
    @Test
    void whenGetOperationByIdWithInvalidId_thenThrowException() {
        when(operationRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(OperationTypeNotFoundException.class, () -> operationService.getOperationById(1));
    }

    /**
     * Test getOperationByType method with a valid type.
     * Expects the operation to be returned.
     */
    @Test
    void whenGetOperationByTypeWithValidType_thenReturnOperation() {
        Operation operation = new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION).setCost(10.0);
        when(operationRepository.findByOperationType(OperationTypeEnum.ADDITION)).thenReturn(Optional.of(operation));

        Operation result = operationService.getOperationByType(OperationTypeEnum.ADDITION);

        assertEquals(OperationTypeEnum.ADDITION, result.getOperationType());
    }

    /**
     * Test getOperationByType method with an invalid type.
     * Expects an OperationTypeNotFoundException to be thrown.
     */
    @Test
    void whenGetOperationByTypeWithInvalidType_thenThrowException() {
        when(operationRepository.findByOperationType(OperationTypeEnum.ADDITION)).thenReturn(Optional.empty());

        assertThrows(OperationTypeNotFoundException.class, () -> operationService.getOperationByType(OperationTypeEnum.ADDITION));
    }

}