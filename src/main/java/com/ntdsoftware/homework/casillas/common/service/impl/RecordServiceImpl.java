package com.ntdsoftware.homework.casillas.common.service.impl;

import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.controller.request.QueryRecordRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.controller.response.RecordResponse;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.Record;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.entity.specificationBuilder.RecordSpecificationBuilder;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.InvalidUsernameException;
import com.ntdsoftware.homework.casillas.common.exception.NoResultException;
import com.ntdsoftware.homework.casillas.common.exception.RecordNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.RecordRepository;
import com.ntdsoftware.homework.casillas.common.service.ICommonService;
import com.ntdsoftware.homework.casillas.common.service.IRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of the IRecordService interface.
 * This service provides operations related to creating records based on user operations.
 *
 * @see IRecordService
 * @see RecordRepository
 * @see ICommonService
 */
@Service
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

    @Override
    public List<RecordResponse> getAllRecords() {

        log.info("Retrieving all records.");

        List<Record> recordList = recordRepository.findAll();

        if (recordList.isEmpty()) {
            log.info("There are no records to return.");
            throw new NoResultException();
        }

        List<RecordResponse> recordResponseList = recordList.stream()
                .map(Record::mapToResponse)
                .toList();

        log.info("Retrieved {} records.", recordResponseList.size());

        return recordResponseList;
    }

    @Override
    public Page<RecordResponse> searchRecords(DataQueryRequest<QueryRecordRequest> queryRequest) {

        RecordSpecificationBuilder specificationBuilder = new RecordSpecificationBuilder()
                .setAmount(queryRequest.getQuery().getAmount())
                .setUserBalance(queryRequest.getQuery().getUserBalance())
                .setResponse(queryRequest.getQuery().getResponse())
                .setDateFrom(queryRequest.getQuery().getDateFrom())
                .setDateTo(queryRequest.getQuery().getDateTo())
                .setIsDeleted(queryRequest.getQuery().getIsDeleted());

        if (null != queryRequest.getQuery().getUserName()) {
            User user = commonService.getUserByUsername(queryRequest.getQuery().getUserName());
            specificationBuilder.setUser(user);
        }

        if (null != queryRequest.getQuery().getOperation()) {
            OperationTypeEnum operationType = OperationTypeEnum.getOperationType(queryRequest.getQuery().getOperation());
            Operation operation = commonService.getOperationByType(operationType);
            specificationBuilder.setOperation(operation);
        }

        Pageable pageable = PageRequest.of(queryRequest.getPage(), queryRequest.getSize(), Sort.by(queryRequest.getSorts()));
        Specification<Record> specification = specificationBuilder.buildSpecification();

        Page<Record> records = (null != specification) ? recordRepository.findAll(specification, pageable) : recordRepository.findAll(pageable);

        if (records.getNumberOfElements() < 1) {
            log.info("No records found with current search options: {}.", queryRequest.getQuery());
            throw new NoResultException();
        }

        log.info("Records retrieved: {} in total page(s): {}", records.getTotalElements(), records.getTotalPages());
        return records.map(Record::mapToResponse);
    }

    @Override
    public Page<RecordResponse> searchUserRecords(int idUser, DataQueryRequest<QueryRecordRequest> queryRequest) {

        User user = commonService.getUserById(idUser);

        if (null != queryRequest.getQuery().getUserName() &&
                !queryRequest.getQuery().getUserName().equals(user.getUsername())) {

            throw new InvalidUsernameException(user.getUsername());
        }

        queryRequest.getQuery().setUserName(user.getUsername());

        return searchRecords(queryRequest);
    }

    @Override
    public void deleteRecord(int recordId) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RecordNotFoundException(recordId));
        record.setDeleted(true);

        recordRepository.saveAndFlush(record);
        log.info("Record has ben deleted: {}", record);
    }

    @Override
    public void deleteUserRecord(int idUser, int recordId) {
        User user = commonService.getUserById(idUser);
        Record record = recordRepository.findByIdAndUser(recordId, user)
                .orElseThrow(() -> new RecordNotFoundException(recordId));

        record.setDeleted(true);
        recordRepository.saveAndFlush(record);
        log.info("User's record has been deleted: {}", record);
    }

}