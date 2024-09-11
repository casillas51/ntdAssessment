package com.ntdsoftware.homework.casillas.common.service.impl;

import static com.ntdsoftware.homework.casillas.common.utils.ValidationUtils.validation;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.exception.ArithmeticOperationException;
import com.ntdsoftware.homework.casillas.common.service.IAdditionService;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AdditionServiceImpl implements IAdditionService {

    private final IOperationService operationService;

    public AdditionServiceImpl(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResultResponse add(int userId, Double term1, Double term2) {

        if(validation().containsNullValues(userId, term1, term2)) {
            throw new ArithmeticOperationException("Cannot perform addition with null values");
        }

        OperationResultResponse response = operationService.performOperationBalance(userId, operationType);
        response.setResult(add(term1, term2));

        log.info("Performed addition operation for user: {} with terms: {} and {}", userId, term1, term2);

        return response;
    }

    private Double add(Double term1, Double term2) {
        return term1 + term2;
    }
}
