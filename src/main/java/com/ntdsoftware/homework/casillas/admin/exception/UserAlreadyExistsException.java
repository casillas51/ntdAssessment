package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * User already exists exception
 */
public class UserAlreadyExistsException extends ApplicationException {

    /** Message */
    private static final String message = "User %s already exists";

    /**
     * Constructor
     * @param username - Username
     */
    public UserAlreadyExistsException(String username) {
        super(UserAlreadyExistsException.class.getSimpleName(), String.format(message, username), HttpStatus.CONFLICT);
    }
}
