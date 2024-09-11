package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.request.SubtractionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;

/**
 * Service interface for subtraction operations.
 */
public interface ISubtractionService {

    /**
     * The type of operation this service handles.
     */
    OperationTypeEnum operationType = OperationTypeEnum.SUBTRACTION;

    /**
     * Performs a subtraction operation.
     *
     * @param userId the ID of the user performing the operation
     * @param subtractionRequest the request object containing the minuend and subtrahend
     * @return the result of the subtraction operation
     * @throws ArithmeticOperationException if any of the input values are null
     */
    OperationResultResponse subtract(int userId, SubtractionRequest subtractionRequest);
}