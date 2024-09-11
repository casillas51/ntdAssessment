package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

/**
 * OperationTypeNotFoundException is thrown when an operation type is not found.
 */
public class ArithmeticOperationException extends ApplicationException {

    /** Message */
    private static final String message = "Operation cannot be performed by: %s";

    /**
     * Constructor
     * @param errorMessage - Error message
     */
    public ArithmeticOperationException(String errorMessage) {
        super(ArithmeticOperationException.class.getSimpleName(),
                String.format(message, errorMessage), HttpStatus.BAD_REQUEST);
    }

}
