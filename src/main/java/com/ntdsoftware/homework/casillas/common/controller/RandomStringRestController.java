package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.service.IRandomStringService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling random string operations.
 */
@RestController
@RequestMapping("${application.api.version1.user}/operation/random-string")
@Slf4j
public class RandomStringRestController extends CommonRestController {

    /**
     * The random string service to use for performing random string operations.
     */
    private final IRandomStringService randomStringService;

    /**
     * Constructs a new RandomStringRestController with the specified user service and random string service.
     *
     * @param userService the user service to use for user-related operations
     * @param randomStringService the random string service to use for performing random string operations
     */
    public RandomStringRestController(IUserService userService, IRandomStringService randomStringService) {
        super(userService);
        this.randomStringService = randomStringService;
    }

    /**
     * Handles HTTP POST requests for generating a random string.
     *
     * @param request the HTTP request
     * @return the result of the random string operation
     */
    @PostMapping
    public ResponseEntity<OperationResultResponse> generateString(HttpServletRequest request) {

        int userId = getUserId(request);

        log.info("Generate string request: {}", userId);

        OperationResultResponse response = randomStringService.randomString(userId);

        return ResponseEntity.ok(response);
    }
}