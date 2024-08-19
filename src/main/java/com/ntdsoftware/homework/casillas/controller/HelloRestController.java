package com.ntdsoftware.homework.casillas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloRestController {

    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("Hello World for user !");
    }

    @RequestMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Hello World for admin !");
    }

}
