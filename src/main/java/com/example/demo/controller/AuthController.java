package com.example.demo.controller;

import com.example.demo.requests.ChangePassRequest;
import com.example.demo.requests.LoginRequest;
import com.example.demo.requests.RegisterRequest;
import com.example.demo.responses.AuthResponse;
import com.example.demo.responses.RegisterResponse;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;   //implement

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest)
    {
        final String authHeader = httpRequest.getHeader("Authorization");
        authService.logout(authHeader.substring(7));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassRequest changePassRequest, HttpServletRequest request) throws AuthenticationException {
        final String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(authService.changePassword(changePassRequest, authHeader));

    }

}
