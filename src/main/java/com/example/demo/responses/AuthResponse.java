package com.example.demo.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {

    String token;
    String tokenType;
    String userId;
    String username;

}

