package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * User not found exception
 */
public class UserNotFoundException extends ApplicationException {

    /** Message */
    private static final String message = "User %d not found";

    /**
     * Constructor
     * @param userId - User id
     */
    public UserNotFoundException(int userId) {
        super(UserNotFoundException.class.getSimpleName(), String.format(message, userId), HttpStatus.NOT_FOUND);
    }
}
