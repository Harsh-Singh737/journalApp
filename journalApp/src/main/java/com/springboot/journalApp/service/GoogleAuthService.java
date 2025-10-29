package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import com.springboot.journalApp.utilis.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class GoogleAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> handleGoogleCallback(String code) {
        try {
            // Step 1: Exchange code for access token and ID token
            String tokenEndpoint = "https://oauth2.googleapis.com/token";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

            String idToken = (String) tokenResponse.getBody().get("id_token");

            // Step 2: Get user info from Google
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to verify Google token");
            }

            Map<String, Object> userInfo = userInfoResponse.getBody();
            String email = (String) userInfo.get("email");

            // Step 3: Create user if not exists
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(email);
            } catch (Exception e) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUserName(email);
                newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                newUser.setRoles(Collections.singletonList("USER"));
                userRepository.save(newUser);
            }

            // Step 4: Generate JWT token
            String jwtToken = jwtUtil.generateToken(email);

            return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));

        } catch (Exception e) {
            log.error("Error handling Google callback", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during Google authentication");
        }
    }
}