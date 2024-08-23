package com.ntdsoftware.homework.casillas.admin.controller;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${application.api.version1.admin}/user")
@Validated
public class UserRestController {

    /**
     * Create a new user
     * @param userRequest - User request
     * @return new UserResponse
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @NotNull(message = "User request is required") UserRequest userRequest) {
        return ResponseEntity.ok(UserResponse.builder().build());
    }

}
