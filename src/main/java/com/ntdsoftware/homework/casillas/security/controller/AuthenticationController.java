package com.ntdsoftware.homework.casillas.security.controller;

import com.ntdsoftware.homework.casillas.controller.response.BasicResponse;
import com.ntdsoftware.homework.casillas.security.controller.request.LoginUserRequest;
import com.ntdsoftware.homework.casillas.security.controller.response.LoginUserResponse;
import com.ntdsoftware.homework.casillas.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Value("${security.authentication.token.header}")
    protected String authorizationHeader;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest userRequest) {

        log.info("Login request received for user: {}", userRequest.getUsername());
        return ResponseEntity.ok(authenticationService.authenticate(userRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity<BasicResponse> logout(HttpServletRequest request) {

        log.info("Logout request received");

        final String authHeader = request.getHeader(authorizationHeader);
        authenticationService.logout(authHeader);

        return ResponseEntity.ok(new BasicResponse("OK", "Logout successful"));
    }
}
