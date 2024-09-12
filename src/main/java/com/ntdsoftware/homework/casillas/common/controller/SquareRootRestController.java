package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.SquareRootRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.service.ISquareRootService;
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
 * REST controller for handling square root operations.
 */
@RestController
@RequestMapping("${application.api.version1.user}/operation/square-root")
@Slf4j
public class SquareRootRestController extends CommonRestController {

    /**
     * The square root service to use for performing square root operations.
     */
    private final ISquareRootService squareRootService;

    /**
     * Constructs a new SquareRootRestController with the specified user service and square root service.
     *
     * @param userService the user service to use for user-related operations
     * @param squareRootService the square root service to use for performing square root operations
     */
    public SquareRootRestController(IUserService userService, ISquareRootService squareRootService) {
        super(userService);
        this.squareRootService = squareRootService;
    }

    /**
     * Handles HTTP POST requests for performing a square root operation.
     *
     * @param request the HTTP request
     * @param squareRootRequest the request object containing the radicand
     * @return the result of the square root operation
     */
    @PostMapping
    public ResponseEntity<OperationResultResponse> squareRoot(
            HttpServletRequest request,
            @NotNull(message = "Square root request is required")
            @Valid @RequestBody SquareRootRequest squareRootRequest) {

        int userId = getUserId(request);

        log.info("Square root request: {} - {}", userId, squareRootRequest.toString());

        OperationResultResponse response = squareRootService.squareRoot(userId, squareRootRequest);

        return ResponseEntity.ok(response);
    }

}
