package com.ntdsoftware.homework.casillas.common.service.impl;

import static com.ntdsoftware.homework.casillas.common.utils.ValidationUtils.validation;

import com.ntdsoftware.homework.casillas.common.controller.request.SubtractionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import com.ntdsoftware.homework.casillas.common.service.ISubtractionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the ISubtractionService interface.
 * Provides functionality to perform subtraction operations.
 */
@Service
@Slf4j
public class SubtractionServiceImpl implements ISubtractionService {

    /**
     * The operation service to use for performing operations.
     */
    private final IOperationService operationService;

    /**
     * Constructs a new SubtractionServiceImpl with the specified operation service.
     *
     * @param operationService the operation service to use for performing operations
     */
    public SubtractionServiceImpl(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResultResponse subtract(int userId, SubtractionRequest subtractionRequest) {

        if (validation().containsNullValues(userId, subtractionRequest.getMinuend(), subtractionRequest.getSubtrahend())) {
            throw new ArithmeticOperationException("Cannot perform subtraction with null values");
        }

        Double minuend = subtractionRequest.getMinuend();
        Double subtrahend = subtractionRequest.getSubtrahend();

        OperationResultResponse response = operationService.performOperationBalance(userId, operationType);
        response.setResult(subtract(minuend, subtrahend));

        log.info("Performed subtraction operation for user: {} with minuend: {} and subtrahend: {}", userId, minuend, subtrahend);

        return response;
    }

    /**
     * Helper method to perform the actual subtraction.
     *
     * @param minuend the number from which another number (the subtrahend) is to be subtracted
     * @param subtrahend the number to be subtracted from the minuend
     * @return the result of the subtraction
     */
    private Double subtract(Double minuend, Double subtrahend) {
        return minuend - subtrahend;
    }
}