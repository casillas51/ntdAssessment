package com.ntdsoftware.homework.casillas.admin.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Active is required")
    private Boolean active;

    @NotBlank(message = "Role is required")
    private String role;

}