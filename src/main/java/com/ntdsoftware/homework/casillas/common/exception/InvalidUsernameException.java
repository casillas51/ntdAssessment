package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the username in the query request does not match the user's username.
 */
public class InvalidUsernameException extends ApplicationException {

    /** The error message for the exception. */
    private static final String message = "The username in the query request does not match the user's username %s.";

    /**
     * Constructs a new InvalidUsernameException with the specified username.
     *
     * @param username the username that caused the exception
     */
    public InvalidUsernameException(String username) {
        super(InvalidUsernameException.class.getSimpleName(),
                String.format(message, username), HttpStatus.CONFLICT);
    }

}