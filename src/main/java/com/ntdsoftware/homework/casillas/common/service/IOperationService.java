package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;

/**
 * Service interface for managing operations.
 * Provides methods to retrieve operations by ID and type.
 */
public interface IOperationService {

    /**
     * Retrieves an operation by its ID.
     *
     * @param id the ID of the operation
     * @return the found operation, or throws OperationTypeNotFoundException if not found
     */
    Operation getOperationById(int id);

    /**
     * Retrieves an operation by its type.
     *
     * @param operationType the type of the operation
     * @return the found operation, or throws OperationTypeNotFoundException if not found
     */
    Operation getOperationByType(OperationTypeEnum operationType);
}