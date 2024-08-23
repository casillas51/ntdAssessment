package com.ntdsoftware.homework.casillas.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends Exception {

    private final String className;

    private final String message;

    private final HttpStatus httpStatus;

    public ApplicationException(String className, String message, HttpStatus httpStatus) {
        super(message);
        this.className = className;
        this.message = message;
        this.httpStatus = httpStatus;
    }


}
