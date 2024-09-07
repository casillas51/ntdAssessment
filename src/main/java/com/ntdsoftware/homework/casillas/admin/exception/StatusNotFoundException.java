package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * StatusNotFoundException is thrown when a requested status is not found in the system.
 * It extends the ApplicationException class and provides a specific message for the missing status.
 */
public class StatusNotFoundException extends ApplicationException {

    /** Message template for the exception */
    private static final String message = "Status %s not found";

    /**
     * Constructs a new StatusNotFoundException with the specified status.
     *
     * @param status the status that was not found
     */
    public StatusNotFoundException(String status) {
        super(StatusNotFoundException.class.getSimpleName(),
                String.format(message, status), HttpStatus.NOT_FOUND);
    }
}
