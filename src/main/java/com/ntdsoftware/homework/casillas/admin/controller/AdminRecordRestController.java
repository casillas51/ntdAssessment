package com.ntdsoftware.homework.casillas.admin.controller;

import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.controller.request.QueryRecordRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.RecordResponse;
import com.ntdsoftware.homework.casillas.common.service.IRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing records in the admin context.
 * Provides endpoints to retrieve, search, and delete records.
 */
@RestController
@RequestMapping("${application.api.version1.admin}/record")
@Slf4j
public class AdminRecordRestController {

    /**
     * The IRecordService instance to manage records.
     */
    private final IRecordService recordService;

    /**
     * Constructs a new AdminRecordRestController with the given IRecordService.
     *
     * @param recordService the IRecordService instance to manage records
     */
    public AdminRecordRestController(IRecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Retrieves all records.
     *
     * @return a ResponseEntity containing a list of all RecordResponse objects
     */
    @GetMapping
    public ResponseEntity<List<RecordResponse>> getAllRecords() {
        log.info("Get all records");
        List<RecordResponse> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    /**
     * Searches for records based on the given query request.
     *
     * @param queryRequest the DataQueryRequest containing the query parameters
     * @return a ResponseEntity containing a page of RecordResponse objects matching the query
     */
    @PostMapping
    public ResponseEntity<Page<RecordResponse>> searchRecords(@NotNull(message = "Record request is required")
            @RequestBody @Valid DataQueryRequest<QueryRecordRequest> queryRequest) {
        log.info("Search records: {}", queryRequest);
        Page<RecordResponse> records = recordService.searchRecords(queryRequest);
        return ResponseEntity.ok(records);
    }

    /**
     * Deletes a record with the given ID.
     *
     * @param recordId the ID of the record to be deleted
     * @return a ResponseEntity with HTTP status ACCEPTED
     */
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable int recordId) {
        log.info("Delete record: {}", recordId);
        recordService.deleteRecord(recordId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}