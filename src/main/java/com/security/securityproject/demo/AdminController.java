package com.security.securityproject.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/management")
public class AdminController {

    @GetMapping
    public ResponseEntity<String> management(){
        return ResponseEntity.ok("Only access to ADMIN role");
    }
}
