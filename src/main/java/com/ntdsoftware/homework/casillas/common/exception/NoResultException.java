package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

public class NoResultException extends ApplicationException {

    public NoResultException() {
        super(NoResultException.class.getSimpleName(), "No result found", HttpStatus.NOT_FOUND);
    }

}
