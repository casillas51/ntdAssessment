package com.ntdsoftware.homework.casillas.unit.service.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.controller.request.QueryRecordRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.controller.response.RecordResponse;
import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.Record;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.common.exception.NoResultException;
import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;
import com.ntdsoftware.homework.casillas.common.repository.RecordRepository;
import com.ntdsoftware.homework.casillas.common.service.ICommonService;
import com.ntdsoftware.homework.casillas.common.service.impl.RecordServiceImpl;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the RecordServiceImpl class.
 * This class tests the methods of RecordServiceImpl to ensure they work as expected.
 */
public class RecordServiceTest implements ApplicationTest {

    /**
     * The RecordRepository instance to interact with the database.
     */
    @Mock
    private RecordRepository recordRepository;

    /**
     * The ICommonService instance to perform common operations.
     */
    @Mock
    private ICommonService commonService;

    /**
     * The RecordServiceImpl instance to test.
     */
    @InjectMocks
    private RecordServiceImpl recordService;

    /**
     * Tests that a record is created when valid user and operation details are provided.
     */
    @Test
    void whenCreateRecord_thenRecordIsCreated() {
        User user = new User().setId(1).setUsername("test");
        Operation operation = new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION);
        OperationResultResponse resultResponse = new OperationResultResponse().setDoubleResult(10.0)
                .setBalance(100.00).setCost(10.00).setOperationType(OperationTypeEnum.ADDITION);

        when(commonService.getUserById(1)).thenReturn(user);
        when(commonService.getOperationByType(OperationTypeEnum.ADDITION)).thenReturn(operation);

        assertDoesNotThrow(() -> recordService.createRecord(1, resultResponse));
    }

    /**
     * Tests that an exception is thrown when a record is created with an invalid user.
     */
    @Test
    void whenCreateRecordWithNotExistingUser_thenRecordIsNotCreated() {
        OperationResultResponse resultResponse = new OperationResultResponse().setDoubleResult(10.0)
                .setBalance(100.00).setCost(10.00).setOperationType(OperationTypeEnum.ADDITION);

        when(commonService.getUserById(1)).thenThrow(new UserNotFoundException("1"));

        assertThrows(UserNotFoundException.class, () -> recordService.createRecord(1, resultResponse));
    }

    /**
     * Tests that an exception is thrown when a record is created with an invalid operation type.
     */
    @Test
    void whenCreateRecordWithNotExistingOperationType_thenRecordIsNotCreated() {
        User user = new User().setId(1).setUsername("test");
        OperationResultResponse resultResponse = new OperationResultResponse().setDoubleResult(10.0)
                .setBalance(100.00).setCost(10.00).setOperationType(OperationTypeEnum.ADDITION);

        when(commonService.getUserById(1)).thenReturn(user);
        when(commonService.getOperationByType(OperationTypeEnum.ADDITION)).thenThrow(new OperationTypeNotFoundException("ADDITION"));

        assertThrows(OperationTypeNotFoundException.class, () -> recordService.createRecord(1, resultResponse));
    }

    /**
     * Tests that an exception is thrown when a record is created with an invalid operation result response.
     */
    @Test
    void whenGetAllRecords_thenRecordsAreRetrieved() {
        Record record = new Record().setId(1).setAmount(10.0).setUser(new User().setId(1).setUsername("test"))
                .setOperation(new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION));
        when(recordRepository.findAll()).thenReturn(List.of(record));

        List<RecordResponse> recordResponses = recordService.getAllRecords();

        assertNotNull(recordResponses);
        assertEquals(1, recordResponses.size());
    }

    /**
     * Tests that an exception is thrown when no records are found.
     */
    @Test
    void whenGetAllRecordsWithNoRecords_thenNoResultExceptionIsThrown() {
        when(recordRepository.findAll()).thenReturn(List.of());
        assertThrows(NoResultException.class, () -> recordService.getAllRecords());
    }

    /**
     * Tests that records are retrieved when search filters are provided.
     */
    @Test
    void whenSearchRecords_thenRecordsAreRetrieved() throws IOException {

        prepareRecordRequest();

        QueryRecordRequest queryRecordRequest = new QueryRecordRequest().setUserName("test").setOperation("ADDITION")
                .setAmount(10.0).setUserBalance(100.0).setResponse("Success");
        DataQueryRequest<QueryRecordRequest> queryRequest = new DataQueryRequest<>();
        queryRequest.setQuery(queryRecordRequest).setPage(1).setSize(10);
        User user = new User().setId(1).setUsername("test");
        Operation operation = new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION);

        when(commonService.getUserByUsername("test")).thenReturn(user);
        when(commonService.getOperationByType(OperationTypeEnum.ADDITION)).thenReturn(operation);

        Page<RecordResponse> recordResponses = recordService.searchRecords(queryRequest);

        assertNotNull(recordResponses);
        assertEquals(3, recordResponses.getTotalElements());
        assertTrue(recordResponses.hasContent());
    }

    /**
     * Tests that an exception is thrown when no records are found with the provided search filters.
     */
    @Test
    void whenSearchRecordsWithNoFilters_thenAllRecordsAreRetrieved() throws IOException {

        prepareRecordRequest();

        QueryRecordRequest queryRecordRequest = new QueryRecordRequest();
        DataQueryRequest<QueryRecordRequest> queryRequest = new DataQueryRequest<>();
        queryRequest.setQuery(queryRecordRequest).setPage(1).setSize(10);

        Page<RecordResponse> recordResponses = recordService.searchRecords(queryRequest);

        assertNotNull(recordResponses);
        assertEquals(3, recordResponses.getTotalElements());
        assertTrue(recordResponses.hasContent());
    }

    /**
     * Tests that an exception is thrown when no records are found with the provided search filters.
     */
    @Test
    @SuppressWarnings("unchecked")
    void whenSearchRecordsWithNoDataFound_thenThrowException() {

        QueryRecordRequest queryRecordRequest = new QueryRecordRequest();
        DataQueryRequest<QueryRecordRequest> queryRequest = new DataQueryRequest<>();
        queryRequest.setQuery(queryRecordRequest).setPage(0).setSize(10);

        when(recordRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));
        when(recordRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

        assertThrows(ApplicationException.class, () -> recordService.searchRecords(queryRequest));
    }

    /**
     * Tests that an exception is thrown when an invalid operation type is provided.
     */
    @Test
    void whenSearchWithInvalidOperationType_thenThrowException() {

        QueryRecordRequest queryRecordRequest = new QueryRecordRequest().setOperation("INVALID");
        DataQueryRequest<QueryRecordRequest> queryRequest = new DataQueryRequest<>();
        queryRequest.setQuery(queryRecordRequest).setPage(0).setSize(10);

        assertThrows(ApplicationException.class, () -> recordService.searchRecords(queryRequest));
    }

    /**
     * Tests that an exception is thrown when an invalid user is provided.
     */
    @Test
    void whenDeleteRecord_thenRecordIsDeleted() {
        Record record = new Record().setId(1).setAmount(10.0).setUser(new User().setId(1).setUsername("test"))
                .setOperation(new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION));
        when(recordRepository.findById(1)).thenReturn(java.util.Optional.of(record));

        assertDoesNotThrow(() -> recordService.deleteRecord(1));
    }

    /**
     * Tests that an exception is thrown when a record is deleted with an invalid record id.
     */
    @Test
    void whenSearchUserRecords_thenRecordsAreRetrieved() throws IOException {

        prepareRecordRequest();

        QueryRecordRequest queryRecordRequest = new QueryRecordRequest().setUserName("test").setOperation("ADDITION")
                .setAmount(10.0).setUserBalance(100.0).setResponse("Success");
        DataQueryRequest<QueryRecordRequest> queryRequest = new DataQueryRequest<>();
        queryRequest.setQuery(queryRecordRequest).setPage(1).setSize(10);
        User user = new User().setId(1).setUsername("test");
        Operation operation = new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION);

        when(commonService.getUserById(1)).thenReturn(user);
        when(commonService.getOperationByType(OperationTypeEnum.ADDITION)).thenReturn(operation);

        Page<RecordResponse> recordResponses = recordService.searchUserRecords(1, queryRequest);

        assertNotNull(recordResponses);
        assertEquals(3, recordResponses.getTotalElements());
        assertTrue(recordResponses.hasContent());
    }

    /**
     * Tests that an exception is thrown when no records are found with the provided search filters.
     */
    @Test
    void whenSearchUserRecordsWithDifferentUser_thenThrowException() {

        QueryRecordRequest queryRecordRequest = new QueryRecordRequest().setUserName("other");
        DataQueryRequest<QueryRecordRequest> queryRequest = new DataQueryRequest<>();
        queryRequest.setQuery(queryRecordRequest).setPage(1).setSize(10);

        User user = new User().setId(1).setUsername("test");
        when(commonService.getUserById(1)).thenReturn(user);

        assertThrows(ApplicationException.class, () -> recordService.searchUserRecords(1, queryRequest));
    }

    /**
     * Tests that an exception is thrown when a record is deleted with an invalid record id.
     */
    @Test
    void whenDeleteRecordWithNotExistingRecord_thenRecordIsNotDeleted() {
        when(recordRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(ApplicationException.class, () -> recordService.deleteRecord(1));
    }

    /**
     * Tests that an exception is thrown when a record is deleted with an invalid user.
     */
    @Test
    void whenDeleteUserRecord_thenRecordIsDeleted() {
        User user = new User().setId(1).setUsername("test");
        Record record = new Record().setId(1).setAmount(10.0).setUser(new User().setId(1).setUsername("test"))
                .setOperation(new Operation().setId(1).setOperationType(OperationTypeEnum.ADDITION));

        when(commonService.getUserById(anyInt())).thenReturn(user);
        when(recordRepository.findByIdAndUser(anyInt(), any(User.class))).thenReturn(java.util.Optional.of(record));

        assertDoesNotThrow(() -> recordService.deleteUserRecord(1, 1));
    }

    /**
     * Tests that an exception is thrown when a record is deleted with an invalid user.
     */
    @Test
    void whenDeleteUserRecordWithNotExistingUser_thenRecordIsNotDeleted() {
        when(commonService.getUserById(1)).thenThrow(new UserNotFoundException("1"));

        assertThrows(UserNotFoundException.class, () -> recordService.deleteUserRecord(1, 1));
    }

    /**
     * Tests that an exception is thrown when a record is deleted with an invalid record id.
     */
    @Test
    void whenDeleteUserRecordWithNotExistingRecord_thenRecordIsNotDeleted() {
        User user = new User().setId(1).setUsername("test");
        when(commonService.getUserById(1)).thenReturn(user);
        when(recordRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(ApplicationException.class, () -> recordService.deleteUserRecord(1, 1));
    }

    /**
     * Prepares the record request for testing.
     *
     * @throws IOException if an error occurs while reading the JSON file
     */
    @SuppressWarnings("unchecked")
    private void prepareRecordRequest() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Record> recordList = objectMapper.readValue(new File("src/test/resources/json/record-list.json"), new TypeReference<List<Record>>() {});

        Page<Record> recordPage = new PageImpl<>(recordList);
        when(recordRepository.findAll(any(Pageable.class))).thenReturn(recordPage);
        when(recordRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(recordPage);
    }

}