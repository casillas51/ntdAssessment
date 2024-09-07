package com.ntdsoftware.homework.casillas.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidAmountException extends ApplicationException {

    private static final String message = "Amount must be greater than or equal to 0";

    public InvalidAmountException() {
        super(InvalidAmountException.class.getSimpleName(), message, HttpStatus.BAD_REQUEST);
    }
}
