package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends ApplicationException {

    private static final String message = "Role %s not found";

    public RoleNotFoundException(String role) {
        super(RoleNotFoundException.class.getSimpleName(),
                String.format(message, role), HttpStatus.NOT_FOUND);
    }

}
