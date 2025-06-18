package com.jj.scheduler.request;

import com.jj.scheduler.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final Role role = Role.USER;
}
