package com.ntdsoftware.homework.casillas.admin.controller.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * QueryUserRequest represents a request object for querying user data.
 * It includes fields for filtering users based on various criteria such as ID, username, status, creation date range, role, and deletion status.
 */
@Data
@Accessors(chain = true)
public class QueryUserRequest {

    /** The username of the user. */
    private String username;

    /** The status of the user. */
    private String status;

    /** The start date for filtering users created from this date. */
    private Timestamp createdDateFrom;

    /** The end date for filtering users created up to this date. */
    private Timestamp createdDateTo;

    /** The role of the user. */
    private String role;

    /** Indicates whether the user is deleted. */
    private Boolean deleted;
}
