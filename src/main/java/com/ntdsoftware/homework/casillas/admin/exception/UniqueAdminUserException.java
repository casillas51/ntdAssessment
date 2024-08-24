package com.ntdsoftware.homework.casillas.admin.exception;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UniqueAdminUserException extends ApplicationException {

    /** Message */
    private static final String message = "There must be at least one admin user";

    /**
     * Constructor
     */
    public UniqueAdminUserException() {
        super(UniqueAdminUserException.class.getSimpleName(), message, HttpStatus.CONFLICT);
    }

}
