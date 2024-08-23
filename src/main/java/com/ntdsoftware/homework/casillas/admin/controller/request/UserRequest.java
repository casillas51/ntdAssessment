package com.ntdsoftware.homework.casillas.admin.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
public class UserRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Active is required")
    private Boolean active;

    @NotBlank(message = "Role is required")
    private String role;

    @Override
    public String toString() {
        return String.format("UserRequest[username=%s, active=%s, role=%s]", username, active, role);
    }

}