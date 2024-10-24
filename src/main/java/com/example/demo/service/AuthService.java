package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.requests.LoginRequest;
import com.example.demo.responses.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    public AuthResponse login(LoginRequest loginRequest);

    public void logout(String token);
}
