package com.ntdsoftware.homework.casillas.admin.service;

import com.ntdsoftware.homework.casillas.admin.controller.request.QueryUserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * IUserService defines the contract for user management operations.
 * It includes methods for creating, retrieving, updating, and deleting users,
 * as well as retrieving all users with pagination and sorting.
 * Implementations of this interface should handle the business logic for user management
 * and interact with the necessary repositories and services.
 */
public interface IUserService {

    /**
     * Creates a new user.
     *
     * @param userRequest the user request containing user information
     * @return the newly created user response
     * @throws ApplicationException if an error occurs during user creation
     */
    @Transactional(rollbackFor = Exception.class)
    UserResponse createUser(UserRequest userRequest) throws ApplicationException;

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user response
     * @throws ApplicationException if an error occurs during user retrieval
     */
    UserResponse getUserById(int userId) throws ApplicationException;

    /**
     * Retrieves a user by their username.
     *
     * @param userName the username of the user to retrieve
     * @return the user response
     * @throws ApplicationException if an error occurs during user retrieval
     */
    int getUserId(String userName) throws ApplicationException;

    /**
     * Updates a user with the given user request.
     *
     * @param userId the ID of the user to update
     * @param userRequest the user request containing updated information
     * @return the updated user response
     * @throws ApplicationException if an error occurs during user update
     */
    @Transactional(rollbackFor = Exception.class)
    UserResponse updateUser(int userId, UserRequest userRequest) throws ApplicationException;

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     * @throws ApplicationException if an error occurs during user deletion
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteUser(int userId) throws ApplicationException;

    /**
     * Retrieves all users with pagination and sorting.
     *
     * @param userRequest the data query request containing pagination and sorting information
     * @return a page of user responses
     * @throws ApplicationException if an error occurs during user retrieval
     */
    Page<UserResponse> getAllUsers(DataQueryRequest<QueryUserRequest> userRequest) throws ApplicationException;
}
