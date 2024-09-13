package com.ntdsoftware.homework.casillas.common.service.impl;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.OperationRepository;
import com.ntdsoftware.homework.casillas.common.service.IBalanceService;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import com.ntdsoftware.homework.casillas.common.service.IRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the IOperationService interface.
 * Provides methods to retrieve operations by ID and type using the OperationRepository.
 */
@Service
@Slf4j
public class OperationServiceImpl implements IOperationService {

    /**
     * The OperationRepository instance to handle operation data.
     */
    private final OperationRepository operationRepository;

    /**
     * The RecordService instance to handle record operations.
     */
    private final IRecordService recordService;

    /**
     * The BalanceService instance to handle balance operations.
     */
    private final IBalanceService balanceService;

    /**
     * Constructs a new OperationServiceImpl with the given OperationRepository and BalanceService.
     *
     * @param operationRepository the OperationRepository instance to handle operation data
     * @param balanceService      the BalanceService instance to handle balance operations
     */
    public OperationServiceImpl(OperationRepository operationRepository,
                                IRecordService recordService, IBalanceService balanceService) {
        this.operationRepository = operationRepository;
        this.recordService = recordService;
        this.balanceService = balanceService;
    }

    @Override
    public OperationResultResponse performOperationBalance(int userId, OperationTypeEnum operationType, Double result) {
        return performOperationBalance(userId, operationType, String.valueOf(result));
    }

    @Override
    public OperationResultResponse performOperationBalance(int userId, OperationTypeEnum operationType, String result) {

        Double cost = getOperationCost(userId, operationType);
        Double balance = balanceService.withdraw(userId, cost);

        OperationResultResponse response = new OperationResultResponse().setOperationType(operationType)
                .setBalance(balance).setCost(cost).setResult(result);

        recordService.createRecord(userId, response);

        return response;
    }

    /**
     * Get the cost of an operation by type.
     *
     * @param userId        the ID of the user to get the operation cost for
     * @param operationType the type of operation to get the cost for
     * @return the cost of the operation
     */
    private Double getOperationCost(int userId, OperationTypeEnum operationType) {

        log.info("Get operation by type: {} - {}", userId, operationType);

        Operation operation = operationRepository.findByOperationType(operationType)
                .orElseThrow(() -> new OperationTypeNotFoundException(operationType.toString()));

        balanceService.hasEnoughBalance(userId, operation.getCost());
        return operation.getCost();
    }
}