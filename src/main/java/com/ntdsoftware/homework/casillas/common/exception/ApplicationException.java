package com.ntdsoftware.homework.casillas.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Application Exception
 */
@Getter
public class ApplicationException extends RuntimeException {

    /** Class name */
    private final String className;

    /** Message */
    private final String message;

    /** HTTP status */
    private final HttpStatus httpStatus;

    /**
     * Construction
     * @param className - Class name
     * @param message - Message
     * @param httpStatus - Http status
     */
    public ApplicationException(String className, String message, HttpStatus httpStatus) {
        super(message);
        this.className = className;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructor
     * @param httpStatus - Http status
     * @param message - Message
     */
    public ApplicationException(HttpStatus httpStatus, String message) {
        super(message);
        this.className = this.getClass().getName();
        this.message = message;
        this.httpStatus = httpStatus;
    }


}
