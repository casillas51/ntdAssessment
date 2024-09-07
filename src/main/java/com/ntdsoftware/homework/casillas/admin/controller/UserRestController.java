package com.ntdsoftware.homework.casillas.admin.controller;

import com.ntdsoftware.homework.casillas.admin.controller.request.QueryUserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Rest Controller
 */
@RestController
@RequestMapping("${application.api.version1.admin}/user")
@Slf4j
public class UserRestController {

    /** User service */
    private final IUserService userService;

    /**
     * Constructor
     * @param userService - User service
     */
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

        UserResponse response = userService.createUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get a user by id
     * @param userId - User id
     * @return UserResponse
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable int userId) throws ApplicationException {

        log.info("Get user: {}", userId);

        UserResponse response = userService.getUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int userId,
                   @RequestBody UserRequest userRequest) throws ApplicationException {

        log.info("Update user: {}", userId);

        UserResponse response = userService.updateUser(userId, userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) throws ApplicationException {

        log.info("Delete user: {}", userId);

        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestBody DataQueryRequest<QueryUserRequest> userRequest) throws ApplicationException {

        log.info("Search user: {}", userRequest.toString());

        Page<UserResponse> response = userService.getAllUsers(userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
