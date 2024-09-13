package com.ntdsoftware.homework.casillas.common.service.impl;

import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.OperationRepository;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import com.ntdsoftware.homework.casillas.common.service.ICommonService;
import org.springframework.stereotype.Service;

/**
 * Implementation of the ICommonService interface.
 * This service provides common operations related to users, such as retrieving and updating user information.
 *
 * @see ICommonService
 * @see UserRepository
 */
@Service
public class CommonServiceImpl implements ICommonService {

    /**
     * The UserRepository instance to interact with the database.
     */
    private final UserRepository userRepository;

    /**
     * The OperationRepository instance to interact with the database.
     */
    private final OperationRepository operationRepository;

    /**
     * Constructs a new CommonServiceImpl with the given UserRepository.
     *
     * @param userRepository the UserRepository instance to interact with the database
     * @param operationRepository the OperationRepository instance to interact with the database
     */
    public CommonServiceImpl(UserRepository userRepository, OperationRepository operationRepository) {
        this.userRepository = userRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Operation getOperationByType(OperationTypeEnum operationType) {
        return operationRepository.findByOperationType(operationType)
                .orElseThrow(() -> new OperationTypeNotFoundException(operationType.toString()));
    }
}