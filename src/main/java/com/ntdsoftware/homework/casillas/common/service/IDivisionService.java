package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.request.DivisionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;

/**
 * Interface for division service.
 * Provides functionality to perform division operations.
 */
public interface IDivisionService {

    /**
     * The operation type for division.
     */
    OperationTypeEnum operationType = OperationTypeEnum.DIVISION;

    /**
     * Performs a division operation.
     *
     * @param accountId the ID of the account performing the operation
     * @param request the request object containing the dividend and divisor
     * @return the result of the division operation
     * @throws ArithmeticOperationException if any of the input values are null
     */
    OperationResultResponse divide(int accountId, DivisionRequest request);
}