package com.jj.scheduler.controller;

import com.jj.scheduler.request.AuthenticationRequest;
import com.jj.scheduler.request.RegisterRequest;
import com.jj.scheduler.response.AuthenticationResponse;
import com.jj.scheduler.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) {
        log.info("Registering new admin with email: " + request.getEmail());
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        log.info("Login by the user with email: " + request.getEmail());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
