package com.jj.scheduler.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String status;
    private String token;
}
