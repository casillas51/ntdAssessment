package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApplicationException {

        private static final String message = "User %s already exists";

        public UserAlreadyExistsException(String username) {
            super(UserAlreadyExistsException.class.getSimpleName(),
                    String.format(message, username), HttpStatus.CONFLICT);
        }
}
