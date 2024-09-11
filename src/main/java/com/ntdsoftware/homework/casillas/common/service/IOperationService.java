package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;

/**
 * Service interface for managing operations.
 * Provides methods to retrieve operations by ID and type.
 */
public interface IOperationService {

    /**
     * Perform a balance operation for a user.
     *
     * @param userId        the ID of the user to perform the operation for
     * @param operationType the type of operation to perform
     * @return the result of the operation
     */
    OperationResultResponse performOperationBalance(int userId, OperationTypeEnum operationType);

}