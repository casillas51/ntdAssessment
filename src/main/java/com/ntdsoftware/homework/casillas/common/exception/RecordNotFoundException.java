package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a record is not found.
 * This exception is used to indicate that a specific record could not be found in the database.
 */
public class RecordNotFoundException extends ApplicationException {

    /**
     * The message template for the exception.
     */
    private static final String message = "Record %d not found.";

    /**
     * Constructs a new RecordNotFoundException with the specified record ID.
     *
     * @param recordId the ID of the record that was not found
     */
    public RecordNotFoundException(int recordId) {
        super(RecordNotFoundException.class.getSimpleName(),
                String.format(message, recordId), HttpStatus.NOT_FOUND);
    }

}