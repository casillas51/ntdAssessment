package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;

/**
 * Service interface for generating random strings.
 */
public interface IRandomStringService {

    /**
     * The operation type for generating random strings.
     */
    OperationTypeEnum operationType = OperationTypeEnum.RANDOM_STRING;

    /**
     * Generates a random string for the specified user.
     *
     * @param idUser the ID of the user
     * @return the result of the random string operation
     */
    OperationResultResponse randomString(int idUser);

}