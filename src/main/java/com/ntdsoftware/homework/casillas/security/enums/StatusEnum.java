package com.ntdsoftware.homework.casillas.security.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

    ACTIVE("active"),

    INACTIVE("inactive");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

}
