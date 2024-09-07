package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.common.controller.response.ValidationErrorResponse;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleException(final HandlerMethodValidationException methodArgumentNotValid) {

        AtomicReference<String> message = new AtomicReference<>();
        List<String> errors = new ArrayList<>();

        methodArgumentNotValid.getAllValidationResults()
                .forEach(violation -> {
                    message.set(String.format("There are %d validation error(s)", violation.getResolvableErrors().size()));
                    violation.getResolvableErrors().forEach(resolvableError -> {
                        errors.add(resolvableError.getDefaultMessage());
                    });
                });

        ValidationErrorResponse validationErrorResponse = ValidationErrorResponse.builder()
                .message(message.toString())
                .errors(errors)
                .build();

        log.error(validationErrorResponse.toString());

        return ResponseEntity.badRequest().body(validationErrorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleException(final HttpMessageNotReadableException httpMessageNotReadable) {

        ValidationErrorResponse validationErrorResponse = ValidationErrorResponse.builder()
                .message("Invalid request body")
                .errors(List.of("Required request body is missing"))
                .build();

        log.error(validationErrorResponse.toString());

        return ResponseEntity.badRequest().body(validationErrorResponse);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ValidationErrorResponse> handleException(final ApplicationException applicationException) {

        ValidationErrorResponse validationErrorResponse = ValidationErrorResponse.builder()
                .message(String.format("Exception thrown: %s", applicationException.getClassName()))
                .errors(List.of(applicationException.getMessage()))
                .build();

        log.error(validationErrorResponse.toString());

        return ResponseEntity.status(applicationException.getHttpStatus()).body(validationErrorResponse);
    }

}
