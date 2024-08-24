package com.ntdsoftware.homework.casillas.admin.service;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.exception.RoleNotFoundException;
import com.ntdsoftware.homework.casillas.admin.exception.UserAlreadyExistsException;
import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
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
     * @throws ApplicationException - Application exception
     */
    @Transactional(rollbackFor = Exception.class)
    UserResponse createUser(UserRequest userRequest) throws ApplicationException;

    /**
     * Get a user by username
     * @param userId - User id
     * @return UserResponse
     * @throws ApplicationException - Application exception
     */
    UserResponse getUser(int userId) throws ApplicationException;

    /**
     * Update a user
     * @param userId - User id
     * @param userRequest - User request
     * @return UserResponse
     * @throws ApplicationException - Application exception
     */
    UserResponse updateUser(int userId, UserRequest userRequest) throws ApplicationException;

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
