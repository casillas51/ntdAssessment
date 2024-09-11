package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.SubtractionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.service.ISubtractionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling subtraction operations.
 */
@RestController
@RequestMapping("${application.api.version1.user}/operation/subtraction")
@Slf4j
public class SubtractionRestController extends CommonRestController {

    private final ISubtractionService subtractionService;

    /**
     * Constructs a new SubtractionRestController with the specified user service and subtraction service.
     *
     * @param userService the user service to use for user-related operations
     * @param subtractionService the subtraction service to use for performing subtraction operations
     */
    public SubtractionRestController(IUserService userService, ISubtractionService subtractionService) {
        super(userService);
        this.subtractionService = subtractionService;
    }

    /**
     * Handles HTTP POST requests for performing a subtraction operation.
     *
     * @param request the HTTP request
     * @param subtractionRequest the request object containing the minuend and subtrahend
     * @return the result of the subtraction operation
     */
    @PostMapping
    public ResponseEntity<OperationResultResponse> subtract(
            HttpServletRequest request,
            @NotNull(message = "Subtraction request is required")
            @Valid @RequestBody SubtractionRequest subtractionRequest) {

        int userId = getUserId(request);

        log.info("Subtraction request: {} - {}", userId, subtractionRequest.toString());

        OperationResultResponse response = subtractionService.subtract(userId, subtractionRequest);

        return ResponseEntity.ok(response);
    }
}