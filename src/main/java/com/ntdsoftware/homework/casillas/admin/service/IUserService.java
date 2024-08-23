package com.ntdsoftware.homework.casillas.admin.service;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;

import java.util.List;

/**
 * User service interface
 */
public interface IUserService {

    /**
     * Create a new user
     * @param userRequest - User request
     * @return new UserResponse
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Get a user by username
     * @param userRequest - User request
     * @return UserResponse
     */
    UserResponse getUser(UserRequest userRequest);

    /**
     * Update a user
     * @param userRequest - User request
     * @return updated UserResponse
     */
    UserResponse updateUser(UserRequest userRequest);

    /**
     * Delete a user
     * @param userRequest - User request
     */
    void deleteUser(UserRequest userRequest);

    /**
     * Get all users
     * @return List<UserResponse>
     */
    List<UserResponse> getAllUsers();




}
