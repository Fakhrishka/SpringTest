package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.requests.ChangePassRequest;
import com.example.demo.requests.LoginRequest;
import com.example.demo.requests.RegisterRequest;
import com.example.demo.responses.AuthResponse;
import com.example.demo.responses.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.AuthenticationException;

public interface AuthService {

    public AuthResponse login(LoginRequest loginRequest);

    public void logout(String token);

    public RegisterResponse register(RegisterRequest registerRequest) throws InstanceAlreadyExistsException;

    public boolean changePassword(ChangePassRequest changePassRequest, String authHeader) throws AuthenticationException;
}
