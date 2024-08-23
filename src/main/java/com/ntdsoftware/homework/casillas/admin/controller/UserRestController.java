package com.ntdsoftware.homework.casillas.admin.controller;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.exception.RoleNotFoundException;
import com.ntdsoftware.homework.casillas.admin.exception.UserAlreadyExistsException;
import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("${application.api.version1.admin}/user")
@Slf4j
public class UserRestController {

    private final IUserService userService;

    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user
     * @param userRequest - User request
     * @return new UserResponse
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @NotNull(message = "User request is required")
            @Valid @RequestBody UserRequest userRequest) throws ApplicationException {

        log.info("Create user: {}", userRequest.toString());

 //       try {

            UserResponse response = userService.createUser(userRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

  /*      } catch (ApplicationException applicationException) {
            throw new ResponseStatusException(applicationException.getHttpStatus(),
                    applicationException.getMessage());

        } */
    }

}
