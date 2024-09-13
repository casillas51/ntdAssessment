package com.ntdsoftware.homework.casillas.common.service.impl;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.exception.RandomApiServiceUnavailable;
import com.ntdsoftware.homework.casillas.common.service.IOperationService;
import com.ntdsoftware.homework.casillas.common.service.IRandomStringService;
import com.ntdsoftware.homework.casillas.common.webClient.IApiRandomClient;
import com.ntdsoftware.homework.casillas.common.webClient.response.GenerateStringResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for generating random strings.
 */
@Service
@Slf4j
public class RandomStringServiceImpl implements IRandomStringService {

    /**
     * The operation service to use for performing operations.
     */
    private final IOperationService operationService;

    /**
     * The API random client to use for generating random strings.
     */
    private final IApiRandomClient apiRandomClient;

    /**
     * Constructs a new RandomStringServiceImpl with the specified operation service and API random client.
     *
     * @param operationService the operation service to use for performing operations
     * @param apiRandomClient the API random client to use for generating random strings
     */
    public RandomStringServiceImpl(IOperationService operationService, IApiRandomClient apiRandomClient) {
        this.operationService = operationService;
        this.apiRandomClient = apiRandomClient;
    }

    /**
     * Generates a random string for the specified user.
     *
     * @param idUser the ID of the user
     * @return the result of the random string operation
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResultResponse randomString(int idUser) {

        String result = randomString();
        OperationResultResponse response = operationService.performOperationBalance(idUser, operationType, result);

        log.info("Performed random string operation for user: {}", idUser);

        return response;
    }

    /**
     * Generates a random string using the API random client.
     *
     * @return the generated random string
     * @throws RandomApiServiceUnavailable if the API random service is unavailable
     */
    private String randomString() {
        GenerateStringResponse generateStringResponse = apiRandomClient.generateString();

        log.info("ApiRandom service response {}", generateStringResponse);

        if (null == generateStringResponse.getResult()) {
            throw new RandomApiServiceUnavailable();
        }

        return generateStringResponse.getResult().getRandom().getData()[0];
    }
}