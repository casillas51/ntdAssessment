package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.MultiplicationRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.service.IMultiplicationService;
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
 * REST controller for handling multiplication operations.
 */
@RestController
@RequestMapping("${application.api.version1.user}/operation/multiplication")
@Slf4j
public class MultiplicationRestController extends CommonRestController {

    /**
     * The multiplication service to use for performing multiplication operations.
     */
    private final IMultiplicationService multiplicationService;

    /**
     * Constructs a new MultiplicationRestController with the specified user service and multiplication service.
     *
     * @param userService the user service to use for user-related operations
     * @param multiplicationService the multiplication service to use for performing multiplication operations
     */
    public MultiplicationRestController(IUserService userService, IMultiplicationService multiplicationService) {
        super(userService);
        this.multiplicationService = multiplicationService;
    }

    /**
     * Handles HTTP POST requests for performing a multiplication operation.
     *
     * @param request the HTTP request
     * @param multiplicationRequest the request object containing the multiplicand and multiplier
     * @return the result of the multiplication operation
     */
    @PostMapping
    public ResponseEntity<OperationResultResponse> multiply(
            HttpServletRequest request,
            @NotNull(message = "Multiplication request is required")
            @Valid @RequestBody MultiplicationRequest multiplicationRequest) {

        int userId = getUserId(request);

        log.info("Multiplication request: {} - {}", userId, multiplicationRequest.toString());

        OperationResultResponse response = multiplicationService.multiply(userId, multiplicationRequest);

        return ResponseEntity.ok(response);
    }
}