package com.ntdsoftware.homework.casillas.common.service.impl;

import static com.ntdsoftware.homework.casillas.common.utils.ValidationUtils.validation;

import com.ntdsoftware.homework.casillas.common.controller.request.MultiplicationRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.IMultiplicationService;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the IMultiplicationService interface.
 * Provides functionality to perform multiplication operations.
 */
@Service
@Slf4j
public class MultiplicationServiceImpl implements IMultiplicationService {

    /**
     * The operation service to use for performing operations.
     */
    private final IOperationService operationService;

    /**
     * Constructs a new MultiplicationServiceImpl with the specified operation service.
     *
     * @param operationService the operation service to use for performing operations
     */
    public MultiplicationServiceImpl(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    @Transactional
    public OperationResultResponse multiply(int idUser, MultiplicationRequest request) {

        if(validation().containsNullValues(idUser, request.getMultiplicand(), request.getMultiplier())) {
            throw new ArithmeticOperationException("Cannot perform multiplication with null values");
        }

        Double multiplicand = request.getMultiplicand();
        Double multiplier = request.getMultiplier();

        OperationResultResponse response = operationService.performOperationBalance(idUser, operationType);
        response.setResult(multiply(multiplicand, multiplier));

        log.info("Performed multiplication operation for user: {} with multiplicand: {} and multiplier: {}", idUser, multiplicand, multiplier);

        return response;
    }

    /**
     * Helper method to perform the actual multiplication.
     *
     * @param multiplicand the number to be multiplied
     * @param multiplier the number by which to multiply
     * @return the result of the multiplication
     */
    private Double multiply(Double multiplicand, Double multiplier) {
        return multiplicand * multiplier;
    }
}