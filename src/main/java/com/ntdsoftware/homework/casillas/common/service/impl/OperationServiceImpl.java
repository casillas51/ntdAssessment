package com.ntdsoftware.homework.casillas.common.service.impl;

import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.OperationRepository;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the IOperationService interface.
 * Provides methods to retrieve operations by ID and type using the OperationRepository.
 */
@Service
@Slf4j
public class OperationServiceImpl implements IOperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation getOperationById(int id) {

        log.info("Get operation by ID: {}", id);

        return operationRepository.findById(id)
                .orElseThrow(() -> new OperationTypeNotFoundException(id));
    }

    @Override
    public Operation getOperationByType(OperationTypeEnum operationType) {

        log.info("Get operation by type: {}", operationType);

        return operationRepository.findByOperationType(operationType)
                .orElseThrow(() -> new OperationTypeNotFoundException(operationType.toString()));

    }
}