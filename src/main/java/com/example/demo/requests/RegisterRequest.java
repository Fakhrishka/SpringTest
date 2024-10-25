package com.example.demo.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    String username;
    @NotBlank
    String password;
}
