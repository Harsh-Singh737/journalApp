package com.springboot.journalApp.controller;

import com.springboot.journalApp.config.RateLimiterConfig;
import com.springboot.journalApp.dto.AllUsersDTO;
import com.springboot.journalApp.dto.ApiResponse;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.service.UserService;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RateLimiterConfig limiter;

    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse<List<AllUsersDTO>>> getAllUsers() {
        if (limiter.getBucket().tryConsume(1)) {
            List<AllUsersDTO> users = userService.getAllUsers();

            ApiResponse<List<AllUsersDTO>> response = new ApiResponse<>(
                    "Fetched all users successfully",
                    LocalDateTime.now(),
                    users
            );

            return ResponseEntity.ok(response);
        }else {
            ApiResponse<List<AllUsersDTO>> errorResponse = new ApiResponse<>(
                    "Too Many Requests",
                    LocalDateTime.now(),
                    null
            );

            return ResponseEntity.status(429).body(errorResponse);
        }
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }
}
