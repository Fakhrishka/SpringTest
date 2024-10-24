package com.example.demo.controller;

import com.example.demo.requests.LoginRequest;
import com.example.demo.responses.AuthResponse;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;   //implement

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        System.out.println(loginRequest);
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest)
    {
        final String authHeader = httpRequest.getHeader("Authorization");
        authService.logout(authHeader.substring(7));
         // needs to be fulfilled
    }

//    @PostMapping("/logout")

}
