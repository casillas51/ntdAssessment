package com.ntdsoftware.homework.casillas.common.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ValidationErrorResponse {

    private String message;

    private List<String> errors;

    @Override
    public String toString() {
        return String.format("message=%s: [errors=%s]", message, getErrorsString());
    }

    private String getErrorsString() {
        StringBuffer errorsString = new StringBuffer();
        errors.forEach(error -> {
            errorsString.append(error).append(", ");
        });

        return errorsString.substring(0, errorsString.length() - 2);
    }
}
