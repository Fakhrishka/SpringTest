package com.example.demo.service;

import com.example.demo.entity.TokenBlackList;
import com.example.demo.entity.User;
import com.example.demo.repository.TokenBlackListRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.requests.LoginRequest;
import com.example.demo.responses.AuthResponse;
import com.example.demo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenBlackListRepository tokenBlackListRepository;


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
System.out.println("LINE 31");

        Optional<User> qResult = userRepository.findByUsername(loginRequest.getUsername());
        if(qResult.isPresent())
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()
            ));

            String jwt = jwtTokenProvider.GenerateJWT(authentication);
            return AuthResponse.builder()
                    .token(jwt)
                    .tokenType("BEARER")
                    .username(qResult.get().getUsername())
                    .userId(qResult.get().getId())
                    .build();
        }
        else
            throw new RuntimeException("No User with such Username");

    }

    @Override
    public void logout(String token)
    {
        if(!jwtTokenProvider.isTokenExpired(token))
        {
            tokenBlackListRepository.insert(TokenBlackList.builder()
                    .token(token)
                    .build());

        }
    }
}
