package com.ntdsoftware.homework.casillas.admin.controller.response;

import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Getter
@ToString
public class UserResponse {

    private int id;

    private String username;

    private StatusEnum active;

    private Timestamp createdDate;

    private RolesEnum role;
}
