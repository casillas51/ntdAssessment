package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.DivisionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.service.IDivisionService;
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
 * REST controller for handling division operations.
 */
@RestController
@RequestMapping("${application.api.version1.user}/operation/division")
@Slf4j
public class DivisionRestController extends CommonRestController {

    /**
     * The division service to use for performing division operations.
     */
    private final IDivisionService divisionService;

    /**
     * Constructs a new DivisionRestController with the specified user service and division service.
     *
     * @param userService the user service to use for user-related operations
     * @param divisionService the division service to use for performing division operations
     */
    public DivisionRestController(IUserService userService, IDivisionService divisionService) {
        super(userService);
        this.divisionService = divisionService;
    }

    /**
     * Handles HTTP POST requests for performing a division operation.
     *
     * @param request the HTTP request
     * @param divisionRequest the request object containing the dividend and divisor
     * @return the result of the division operation
     */
    @PostMapping
    public ResponseEntity<OperationResultResponse> divide(
            HttpServletRequest request,
            @NotNull(message = "Division request is required")
            @Valid @RequestBody DivisionRequest divisionRequest) {

        int userId = getUserId(request);

        log.info("Division request: {} - {}", userId, divisionRequest.toString());

        OperationResultResponse response = divisionService.divide(userId, divisionRequest);

        return ResponseEntity.ok(response);
    }
}
