package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

public class NoResultException extends ApplicationException {

    private static final String message = "No result found";

    public NoResultException() {
        super(NoResultException.class.getSimpleName(), message, HttpStatus.NOT_FOUND);
    }

}
