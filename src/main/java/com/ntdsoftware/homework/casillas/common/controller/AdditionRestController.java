package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.AdditionRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.service.IAdditionService;
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
 * REST controller for handling addition operations.
 */
@RestController
@RequestMapping("${application.api.version1.user}/operation/addition")
@Slf4j
public class AdditionRestController extends CommonRestController {

    /**
     * The addition service to use for performing addition operations.
     */
    private final IAdditionService additionService;

    /**
     * Constructs a new AdditionRestController with the specified user service and addition service.
     *
     * @param userService the user service to use for user-related operations
     * @param additionService the addition service to use for performing addition operations
     */
    public AdditionRestController(IUserService userService, IAdditionService additionService) {
        super(userService);
        this.additionService = additionService;
    }

    /**
     * Handles HTTP POST requests for performing an addition operation.
     *
     * @param request the HTTP request
     * @param additionRequest the request object containing the terms to be added
     * @return the result of the addition operation
     */
    @PostMapping
    public ResponseEntity<OperationResultResponse> add(
            HttpServletRequest request,
            @NotNull(message = "Addition request is required")
            @Valid @RequestBody AdditionRequest additionRequest) {

        int userId = getUserId(request);

        log.info("Addition request: {} - {}", userId, additionRequest.toString());

        OperationResultResponse response = additionService.add(userId, additionRequest);

        return ResponseEntity.ok(response);
    }
}