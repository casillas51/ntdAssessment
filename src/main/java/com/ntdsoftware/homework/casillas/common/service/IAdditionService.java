package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.request.AdditionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;

/**
 * Service interface for addition operations.
 */
public interface IAdditionService {

    /**
     * The type of operation this service handles.
     */
    OperationTypeEnum operationType = OperationTypeEnum.ADDITION;

    /**
     * Performs an addition operation.
     *
     * @param userId the ID of the user performing the operation
     * @param additionRequest the request object containing the terms to be added
     * @return the result of the addition operation
     * @throws ArithmeticOperationException if any of the input values are null
     */
    OperationResultResponse add(int userId, AdditionRequest additionRequest);
}