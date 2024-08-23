package com.ntdsoftware.homework.casillas.admin.controller.response;

import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * UserResponse
 */
@Builder
@Getter
@ToString
public class UserResponse {

    /** The id of the user */
    private int id;

    /** The username of the user */
    private String username;

    /** The email of the user */
    private StatusEnum active;

    /** The created date of the user */
    private Timestamp createdDate;

    /** The role of the user */
    private RolesEnum role;
}
