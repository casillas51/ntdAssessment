package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * User not found exception
 */
public class UserNotFoundException extends ApplicationException {

    /** Message */
    private static final String messageUserId = "User %d not found";

    private static final String messageUsername = "User %s not found";

    /**
     * Constructor
     * @param userId - User id
     */
    public UserNotFoundException(int userId) {
        super(UserNotFoundException.class.getSimpleName(), String.format(messageUserId, userId), HttpStatus.NOT_FOUND);
    }

    /**
     * Constructor
     * @param username - Username
     */
    public UserNotFoundException(String username) {
        super(UserNotFoundException.class.getSimpleName(), String.format(messageUsername, username), HttpStatus.NOT_FOUND);
    }

    /**
     * Constructor
     */
    public UserNotFoundException() {
        super(UserNotFoundException.class.getSimpleName(), "User not found", HttpStatus.NOT_FOUND);
    }
}
