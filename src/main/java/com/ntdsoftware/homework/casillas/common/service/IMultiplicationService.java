package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.request.MultiplicationRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;

/**
 * Service interface for performing multiplication operations.
 */
public interface IMultiplicationService {

    /**
     * The type of operation this service performs.
     */
    OperationTypeEnum operationType = OperationTypeEnum.MULTIPLICATION;

    /**
     * Performs a multiplication operation.
     *
     * @param idUser the ID of the user performing the operation
     * @param request the request object containing the multiplicand and multiplier
     * @return the result of the multiplication operation
     * @throws ArithmeticOperationException if any of the input values are null
     */
    OperationResultResponse multiply(int idUser, MultiplicationRequest request);

}