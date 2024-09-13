package com.ntdsoftware.homework.casillas.common.service.impl;

import static com.ntdsoftware.homework.casillas.common.utils.ValidationUtils.validation;

import com.ntdsoftware.homework.casillas.common.controller.request.AdditionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.IAdditionService;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the IAdditionService interface.
 * Provides functionality to perform addition operations.
 */
@Service
@Slf4j
public class AdditionServiceImpl implements IAdditionService {

    /**
     * The operation service to use for performing operations.
     */
    private final IOperationService operationService;

    /**
     * Constructs a new AdditionServiceImpl with the specified operation service.
     *
     * @param operationService the operation service to use for performing operations
     */
    public AdditionServiceImpl(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResultResponse add(int userId, AdditionRequest additionRequest) {

        if(validation().containsNullValues(userId, additionRequest.getTerm1(), additionRequest.getTerm2())) {
            throw new ArithmeticOperationException("Cannot perform addition with null values");
        }

        Double term1 = additionRequest.getTerm1();
        Double term2 = additionRequest.getTerm2();

        OperationResultResponse response = operationService.performOperationBalance(userId, operationType);
        response.setDoubleResult(add(term1, term2));

        log.info("Performed addition operation for user: {} with terms: {} and {}", userId, term1, term2);

        return response;
    }

    /**
     * Helper method to perform the actual addition.
     *
     * @param term1 the first term to be added
     * @param term2 the second term to be added
     * @return the result of the addition
     */
    private Double add(Double term1, Double term2) {
        return term1 + term2;
    }
}