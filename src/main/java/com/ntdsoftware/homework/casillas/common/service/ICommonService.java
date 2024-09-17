package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;

/**
 * Service interface for common operations.
 * Provides methods to retrieve users and operations.
 */
public interface ICommonService {

    /**
     * Retrieves the User instance with the given ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User instance
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    User getUserById(int userId);

    /**
     * Retrieves the User instance with the given username.
     *
     * @param username the username of the user to retrieve
     * @return the User instance
     * @throws UserNotFoundException if the user with the given username is not found
     */
    User getUserByUsername(String username);

    /**
     * Updates the given User instance in the database.
     *
     * @param user the User instance to update
     * @return the updated User instance
     */
    User updateUser(User user);

    /**
     * Retrieves the Operation instance with the given type.
     *
     * @param operationType the type of operation to retrieve
     * @return the Operation instance
     * @throws OperationTypeNotFoundException if the operation with the given type is not found
     */
    Operation getOperationByType(OperationTypeEnum operationType);

}
