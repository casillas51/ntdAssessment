package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.request.SquareRootRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;

/**
 * Interface for the square root service.
 * Provides functionality to perform square root operations.
 */
public interface ISquareRootService {

    /**
     * The operation type for square root operations.
     */
    OperationTypeEnum operationType = OperationTypeEnum.SQUARE_ROOT;

    /**
     * Performs a square root operation for the specified user with the specified request.
     *
     * @param userId the ID of the user for which to perform the operation
     * @param request the request object containing the radicand value
     * @return the response object containing the result of the operation
     * @throws ArithmeticOperationException if any of the input values are null
     */
    OperationResultResponse squareRoot(int userId, SquareRootRequest request);
}
