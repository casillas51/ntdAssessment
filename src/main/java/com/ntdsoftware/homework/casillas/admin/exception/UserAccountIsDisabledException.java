package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when an operation is attempted on a user account that is disabled.
 * It extends the ApplicationException class, which is a custom exception class for the application.
 */
public class UserAccountIsDisabledException extends ApplicationException {

    /**
     * The message to be displayed when this exception is thrown.
     */
    private static final String message = "Account for user %s is disabled";

    /**
     * Constructor for UserAccountIsDisabledException.
     *
     * @param username The username of the user whose account is disabled.
     */
    public UserAccountIsDisabledException(String username) {
        super(UserAccountIsDisabledException.class.getSimpleName(),
                String.format(message, username), HttpStatus.UNAUTHORIZED);
    }
}
