package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.controller.request.QueryRecordRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.RecordResponse;
import com.ntdsoftware.homework.casillas.common.service.IRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing user-specific records.
 * Provides endpoints to search and delete user records.
 */
@RestController
@RequestMapping("${application.api.version1.user}/record")
@Slf4j
public class RecordRestController extends CommonRestController {

    /**
     * The IRecordService instance to manage records.
     */
    private final IRecordService recordService;

    /**
     * Constructs a new RecordRestController with the given IUserService and IRecordService.
     *
     * @param userService the IUserService instance to manage users
     * @param recordService the IRecordService instance to manage records
     */
    public RecordRestController(IUserService userService, IRecordService recordService) {
        super(userService);
        this.recordService = recordService;
    }

    /**
     * Searches for user records based on the given query request.
     *
     * @param request the HttpServletRequest containing the user information
     * @param queryRequest the DataQueryRequest containing the query parameters
     * @return a ResponseEntity containing a page of RecordResponse objects matching the query
     */
    @PostMapping
    public ResponseEntity<Page<RecordResponse>> searchRecords(
            HttpServletRequest request,
            @NotNull(message = "Record request is required")
            @RequestBody @Valid DataQueryRequest<QueryRecordRequest> queryRequest) {

        int userId = getUserId(request);

        log.info("Search records: {}, {}", userId, queryRequest.toString());

        Page<RecordResponse> records = recordService.searchUserRecords(userId, queryRequest);
        return ResponseEntity.ok(records);
    }

    /**
     * Deletes a user-specific record with the given ID.
     *
     * @param request the HttpServletRequest containing the user information
     * @param recordId the ID of the record to be deleted
     * @return a ResponseEntity with HTTP status ACCEPTED
     */
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(
            HttpServletRequest request,
            @PathVariable int recordId) {

        int userId = getUserId(request);

        log.info("Delete record: {}, {}", userId, recordId);

        recordService.deleteUserRecord(userId, recordId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}