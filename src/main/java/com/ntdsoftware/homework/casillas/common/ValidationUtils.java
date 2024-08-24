package com.ntdsoftware.homework.casillas.common;

public class ValidationUtils {

    private static ValidationUtils singleton = null;

    private ValidationUtils() {

    }

    public static ValidationUtils validate() {

        if (null == singleton) {
            singleton = new ValidationUtils();
        }

        return singleton;
    }

    public boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
