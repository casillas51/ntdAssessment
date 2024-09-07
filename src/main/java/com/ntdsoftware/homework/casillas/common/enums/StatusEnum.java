package com.ntdsoftware.homework.casillas.common.enums;

import com.ntdsoftware.homework.casillas.admin.exception.StatusNotFoundException;
import lombok.Getter;

/**
 * StatusEnum represents the different statuses available in the system.
 * It includes methods to retrieve a status based on a string value.
 */
@Getter
public enum StatusEnum {

    /** Active status */
    ACTIVE,

    /** Inactive status */
    INACTIVE;

    /**
     * Retrieves the StatusEnum value corresponding to the provided status string.
     *
     * @param status the status string to be converted to a StatusEnum
     * @return the corresponding StatusEnum value
     * @throws StatusNotFoundException if the status string does not match any StatusEnum value
     */
    public static StatusEnum getStatus(String status) {
        for (StatusEnum s : StatusEnum.values()) {
            if (s.name().equals(status.toUpperCase())) {
                return s;
            }
        }

        throw new StatusNotFoundException(status);
    }

}
