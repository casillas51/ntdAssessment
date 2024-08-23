package com.ntdsoftware.homework.casillas.admin.controller;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${application.api.version1.admin}/user")
@Slf4j
public class UserRestController {

    /**
     * Create a new user
     * @param userRequest - User request
     * @return new UserResponse
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @NotNull(message = "User request is required")
            @Valid @RequestBody UserRequest userRequest) {

        log.info("Create user: {}", userRequest.toString());

        return ResponseEntity.ok(UserResponse.builder().build());
    }

}
