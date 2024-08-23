package com.ntdsoftware.homework.casillas.admin.service;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.exception.RoleNotFoundException;
import com.ntdsoftware.homework.casillas.admin.exception.UserAlreadyExistsException;
import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User service interface
 */
public interface IUserService {

    /**
     * Create a new user
     * @param userRequest - User request
     * @return new UserResponse
     * @throws EntityNotFoundException - Entity not found exception
     * @throws UserAlreadyExistsException - User already exists exception
     */
    @Transactional(rollbackFor = Exception.class)
    UserResponse createUser(UserRequest userRequest) throws EntityNotFoundException, UserAlreadyExistsException, RoleNotFoundException;

    /**
     * Get a user by username
     * @param userId - User id
     * @return UserResponse
     */
    UserResponse getUser(int userId) throws UserNotFoundException;

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
