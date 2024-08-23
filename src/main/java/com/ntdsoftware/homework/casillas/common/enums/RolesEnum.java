package com.ntdsoftware.homework.casillas.common.enums;

import com.ntdsoftware.homework.casillas.admin.exception.RoleNotFoundException;

public enum RolesEnum {

    ADMIN,

    USER;

    public String getRole() {
        return this.name();
    }

    public static RolesEnum getRole(String role) throws RoleNotFoundException {

        for(RolesEnum r : RolesEnum.values()) {
            if(r.getRole().equals(role)) {
                return r;
            }
        }

        throw new RoleNotFoundException(role);
    }

}
