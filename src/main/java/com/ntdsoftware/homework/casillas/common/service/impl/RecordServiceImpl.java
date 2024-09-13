package com.ntdsoftware.homework.casillas.common.service.impl;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.Record;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.repository.RecordRepository;
import com.ntdsoftware.homework.casillas.common.service.ICommonService;
import com.ntdsoftware.homework.casillas.common.service.IRecordService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * Implementation of the IRecordService interface.
 * This service provides operations related to creating records based on user operations.
 *
 * @see IRecordService
 * @see RecordRepository
 * @see ICommonService
 */
@Slf4j
public class RecordServiceImpl implements IRecordService {

    /**
     * The RecordRepository instance to interact with the database.
     */
    private final RecordRepository recordRepository;

    /**
     * The ICommonService instance to perform common operations.
     */
    private final ICommonService commonService;

    /**
     * Constructs a new RecordServiceImpl with the given RecordRepository and ICommonService.
     *
     * @param recordRepository the RecordRepository instance to interact with the database
     * @param commonService the ICommonService instance to perform common operations
     */
    public RecordServiceImpl(RecordRepository recordRepository, ICommonService commonService) {
        this.recordRepository = recordRepository;
        this.commonService = commonService;
    }

    @Override
    public void createRecord(int userId, OperationResultResponse operationResultResponse) {

        log.info("Creating record for user with id: {}, operation: {}", userId, operationResultResponse);

        User user = commonService.getUserById(userId);
        Operation operation = commonService.getOperationByType(operationResultResponse.getOperationType());

        Record record = new Record()
                .setOperation(operation)
                .setUser(user)
                .setAmount(operationResultResponse.getCost())
                .setUserBalance(operationResultResponse.getBalance())
                .setOperationResponse(operationResultResponse.getResult())
                .setDate(new Timestamp(System.currentTimeMillis()))
                .setDeleted(false);

        log.info("Record created: {}", record);

        recordRepository.save(record);
    }

}