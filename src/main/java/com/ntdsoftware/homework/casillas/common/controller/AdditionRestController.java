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

@RestController
@RequestMapping("${application.api.version1.user}/operation/addition")
@Slf4j
public class AdditionRestController extends CommonController {

    private final IAdditionService additionService;

    public AdditionRestController(IUserService userService, IAdditionService additionService) {
        super(userService);
        this.additionService = additionService;
    }

    @PostMapping
    public ResponseEntity<OperationResultResponse> add(
            HttpServletRequest request,
            @NotNull(message = "Addition request is required")
            @Valid @RequestBody AdditionRequest additionRequest) {

        int userId = getUserId(request);

        log.info("Addition request: {} - {}", userId, additionRequest.toString());

        OperationResultResponse response = additionService.add(userId, additionRequest.getTerm1(), additionRequest.getTerm2());

        return ResponseEntity.ok(response);
    }

}
