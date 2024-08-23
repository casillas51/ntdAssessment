package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * Role not found exception
 */
public class RoleNotFoundException extends ApplicationException {

    /** Message */
    private static final String message = "Role %s not found";

    /**
     * Constructor
     * @param role - Role
     */
    public RoleNotFoundException(String role) {
        super(RoleNotFoundException.class.getSimpleName(),
                String.format(message, role), HttpStatus.NOT_FOUND);
    }

}
