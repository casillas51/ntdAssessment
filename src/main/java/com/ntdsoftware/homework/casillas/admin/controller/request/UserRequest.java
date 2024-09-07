package com.ntdsoftware.homework.casillas.admin.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * UserRequest represents a request object for creating or updating a user.
 * It includes fields for username, password, active status, and role.
 * The class uses validation annotations to ensure required fields are provided.
 */
@Data
@Accessors(chain = true)
public class UserRequest {

    /** The username of the user. */
    @NotBlank(message = "Username is required")
    private String username;

    /** The password of the user. */
    @NotBlank(message = "Password is required")
    private String password;

    /** The active status of the user. */
    @NotNull(message = "Active is required")
    private Boolean active;

    /** The role of the user. */
    @NotBlank(message = "Role is required")
    private String role;

    @Override
    public String toString() {
        return String.format("UserRequest[username=%s, active=%s, role=%s]", username, active, role);
    }
}