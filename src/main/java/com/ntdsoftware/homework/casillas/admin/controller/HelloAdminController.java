package com.ntdsoftware.homework.casillas.admin.controller;

import com.ntdsoftware.homework.casillas.common.controller.response.BasicResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class HelloAdminController {

    @GetMapping("/hello")
    public ResponseEntity<BasicResponse> hello() {

        return ResponseEntity.ok(new BasicResponse("OK", "Hello World for admin !"));
    }
}
