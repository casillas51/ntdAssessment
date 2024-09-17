package com.ntdsoftware.homework.casillas.common.service.impl;

import static com.ntdsoftware.homework.casillas.common.utils.ValidationUtils.validation;

import com.ntdsoftware.homework.casillas.common.controller.request.SquareRootRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import com.ntdsoftware.homework.casillas.common.service.ISquareRootService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the ISquareRootService interface.
 * Provides functionality to perform square root operations.
 */
@Service
@Slf4j
public class SquareRootServiceImpl implements ISquareRootService {

    /**
     * The operation service to use for performing operations.
     */
    private final IOperationService operationService;

    /**
     * Constructs a new SquareRootServiceImpl with the specified operation service.
     *
     * @param operationService the operation service to use for performing operations
     */
    public SquareRootServiceImpl(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResultResponse squareRoot(int userId, SquareRootRequest request) {

        if (validation().containsNullValues(userId, request.getRadicand())) {
            throw new ArithmeticOperationException("Cannot perform subtraction with null values");
        }

        if (request.getRadicand() < 0) {
            throw new ArithmeticOperationException("Cannot perform square root with negative radicand");
        }

        Double radicand = request.getRadicand();
        Double result = squareRoot(radicand);

        OperationResultResponse response = operationService.performOperationBalance(userId, operationType, result);

        log.info("Performed square root operation for user: {} with radicand: {}", userId, radicand);

        return response;
    }

    /**
     * Helper method to perform the actual square root operation.
     *
     * @param radicand the number for which the square root is to be calculated
     * @return the result of the square root operation
     */
    private Double squareRoot(Double radicand) {
        return Math.sqrt(radicand);
    }
}
