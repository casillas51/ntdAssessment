package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.controller.request.QueryRecordRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.controller.response.RecordResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface for managing records.
 * Provides methods to create records based on user operations.
 */
public interface IRecordService {

    /**
     * Creates a new record for the given user and operation result.
     *
     * @param userId the ID of the user
     * @param operationResultResponse the result of the operation to be recorded
     */
    void createRecord(int userId, OperationResultResponse operationResultResponse);

    /**
     * Retrieves all records.
     *
     * @return a list of all records
     */
    List<RecordResponse> getAllRecords();

    /**
     * Searches for records based on the given query request.
     *
     * @param queryRequest the query request
     * @return a page of records matching the query
     */
    Page<RecordResponse> searchRecords(DataQueryRequest<QueryRecordRequest> queryRequest);

    /**
     * Searches for records of a specific user based on the given query request.
     *
     * @param idUser the ID of the user
     * @param queryRequest the query request
     * @return a page of records matching the query
     */
    Page<RecordResponse> searchUserRecords(int idUser, DataQueryRequest<QueryRecordRequest> queryRequest);

    /**
     * Deletes a record with the given ID.
     *
     * @param recordId the ID of the record to be deleted
     */
    void deleteRecord(int recordId);

    /**
     * Deletes a record for a specific user with the given record ID.
     *
     * @param idUser the ID of the user
     * @param recordId the ID of the record to be deleted
     */
    void deleteUserRecord(int idUser, int recordId);
}