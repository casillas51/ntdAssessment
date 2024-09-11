package com.ntdsoftware.homework.casillas.common.service.impl;

import static com.ntdsoftware.homework.casillas.common.utils.ValidationUtils.validation;

import com.ntdsoftware.homework.casillas.common.controller.request.DivisionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.IDivisionService;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the IDivisionService interface.
 * Provides functionality to perform division operations.
 */
@Service
@Slf4j
public class DivisionServiceImpl implements IDivisionService {

    /**
     * The operation service to use for performing operations.
     */
    private final IOperationService operationService;

    /**
     * Constructs a new DivisionServiceImpl with the specified operation service.
     *
     * @param operationService the operation service to use for performing operations
     */
    public DivisionServiceImpl(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    public OperationResultResponse divide(int accountId, DivisionRequest request) {

        if (validation().containsNullValues(accountId, request.getDividend(), request.getDivisor())) {
            throw new ArithmeticOperationException("Cannot perform division with null values");
        }

        if (request.getDivisor() == 0) {
            throw new ArithmeticOperationException("Cannot divide by zero");
        }

        Double dividend = request.getDividend();
        Double divisor = request.getDivisor();

        OperationResultResponse response = operationService.performOperationBalance(accountId, operationType);
        response.setResult(divide(dividend, divisor));

        log.info("Performed division operation for user: {} with dividend: {} and divisor: {}", accountId, dividend, divisor);

        return response;
    }

    /**
     * Helper method to perform the actual division.
     *
     * @param dividend the number to be divided
     * @param divisor the number by which to divide
     * @return the result of the division
     */
    private Double divide(Double dividend, Double divisor) {
        return dividend / divisor;
    }
}