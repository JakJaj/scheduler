package com.jj.scheduler.service;

import com.jj.scheduler.config.AESPasswordEncoder;
import com.jj.scheduler.config.JwtService;
import com.jj.scheduler.dao.UserRepository;
import com.jj.scheduler.entity.User;
import com.jj.scheduler.exceptions.EntityAlreadyExistsException;
import com.jj.scheduler.exceptions.NoSuchEntityException;
import com.jj.scheduler.request.AuthenticationRequest;
import com.jj.scheduler.request.RegisterRequest;
import com.jj.scheduler.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AESPasswordEncoder aesPasswordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        var user = repository.findByEmail(request.getEmail());
        if (user.isEmpty()) throw new NoSuchEntityException("No user with a specified credentials");
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user.get());
        return AuthenticationResponse.builder()
                .status("Success")
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerUser(RegisterRequest request){
        if (repository.findByEmail(request.getEmail()).isPresent()) throw new EntityAlreadyExistsException("User with this email already exists");

        String safePassword = aesPasswordEncoder.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail())
                .password(safePassword)
                .role(request.getRole())
                .createdAt(request.getCreatedAt())
                .build();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .status("Success")
                .token(jwtToken)
                .build();
    }
}

