package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

/**
 * OperationTypeNotFoundException is thrown when an operation type is not found.
 */
public class OperationTypeNotFoundException extends ApplicationException {

    /** Message */
    private static final String message = "Operation type %s not found";

    /**
     * Constructor
     * @param operationType - Operation type
     */
    public OperationTypeNotFoundException(String operationType) {
        super(OperationTypeNotFoundException.class.getSimpleName(),
                String.format(message, operationType), HttpStatus.NOT_FOUND);
    }

}
