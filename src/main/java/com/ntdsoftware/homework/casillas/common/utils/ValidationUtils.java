package com.ntdsoftware.homework.casillas.common.utils;

public class ValidationUtils {

    private static ValidationUtils singleton = null;

    private ValidationUtils() {

    }

    public static ValidationUtils validation() {

        if (null == singleton) {
            singleton = new ValidationUtils();
        }

        return singleton;
    }

    public boolean containsNullValues(Object... values) {

        for (Object value : values) {
            if (null == value) {
                return true;
            }
        }

        return false;
    }

    public boolean isNullOrEmpty(String... values) {

        for (String value : values) {
            if (null == value || value.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
