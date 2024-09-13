package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;

/**
 * Service interface for managing records.
 * Provides methods to create records based on user operations.
 */
public interface IRecordService {

    /**
     * Creates a new record for the given user and operation result.
     *
     * @param userId the ID of the user
     * @param operationResultResponse the result of the operation to be recorded
     */
    void createRecord(int userId, OperationResultResponse operationResultResponse);

}