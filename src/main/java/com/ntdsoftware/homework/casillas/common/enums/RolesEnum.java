package com.ntdsoftware.homework.casillas.common.enums;

import com.ntdsoftware.homework.casillas.admin.exception.RoleNotFoundException;

/**
 * RolesEnum represents the different roles available in the system.
 * It includes methods to retrieve a role based on a string value.
 */
public enum RolesEnum {

    /** Administrator role */
    ADMIN,

    /** User role */
    USER;

    /**
     * Retrieves the RolesEnum value corresponding to the provided role string.
     *
     * @param role the role string to be converted to a RolesEnum
     * @return the corresponding RolesEnum value
     */
    public static RolesEnum getRole(String role) {

        for (RolesEnum r : RolesEnum.values()) {
            if (r.name().equals(role.toUpperCase())) {
                return r;
            }
        }
        throw new RoleNotFoundException(role);
    }
}