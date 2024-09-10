package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughBalanceException extends ApplicationException {

    private static final String message = "Not enough balance to perform the operation";

    public NotEnoughBalanceException() {
        super(NotEnoughBalanceException.class.getSimpleName(), message, HttpStatus.NOT_ACCEPTABLE);
    }

}
