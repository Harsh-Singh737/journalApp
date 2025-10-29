package com.springboot.journalApp.controller;

import com.springboot.journalApp.service.GoogleAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/google")
@Slf4j
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            return googleAuthService.handleGoogleCallback(code);
        } catch (Exception e) {
            log.error("Exception occurred while handling Google callback", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}