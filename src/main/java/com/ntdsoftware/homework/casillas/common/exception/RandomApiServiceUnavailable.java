package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the Random API service is unavailable.
 */
public class RandomApiServiceUnavailable extends ApplicationException {

    /**
     * The error message for the exception.
     */
    private final static String message = "Random API service is unavailable";

    /**
     * Constructs a new RandomApiServiceUnavailable exception with the default message.
     */
    public RandomApiServiceUnavailable() {
        super(RandomApiServiceUnavailable.class.getSimpleName(), message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}