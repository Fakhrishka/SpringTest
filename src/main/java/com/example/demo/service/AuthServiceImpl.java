package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.TokenBlackList;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TokenBlackListRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.requests.ChangePassRequest;
import com.example.demo.requests.LoginRequest;
import com.example.demo.requests.RegisterRequest;
import com.example.demo.responses.AuthResponse;
import com.example.demo.responses.RegisterResponse;
import com.example.demo.security.jwt.JWTTokenProvider;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final static String USERNOTFOUND = "No User with such username";
    private final static String USERALREADYEXISTS = "User with this Username already exists";
    private final static String PASSWORDMISMATCH = "Passwords is wrong";
    private final static String TOKENEXPIRED = "Token has expired";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TokenBlackListRepository tokenBlackListRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(LoginRequest loginRequest) {

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
            throw new UsernameNotFoundException(USERNOTFOUND);

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

    @Override
    public RegisterResponse register(RegisterRequest registerRequest){
        Optional<User> result = userRepository.findByUsername(registerRequest.getUsername());
        if(result.isPresent()) {
            throw new UsernameNotFoundException(USERALREADYEXISTS);
        }
        else
        {
           String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
            User.builder()
                    .username(registerRequest.getUsername())
                    .password(registerRequest.getPassword())
                    .build();


            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findByRoleName("MANAGER");

            roles.add(roleRepository.findByRoleName("MANAGER"));

            userRepository.save(User.builder()
                    .username(registerRequest.getUsername())
                    .password(encodedPassword)
                    .roles(roles)
                    .build());

            Optional<User> savedUser = userRepository.findByUsername(registerRequest.getUsername());
            if(savedUser.isEmpty())
                throw new RuntimeException("User not saved");

            return RegisterResponse.builder()
                    .id(savedUser.get().getId())
                    .username(registerRequest.getUsername())
                    .roles(roles)
                    .build();
                                
        }
    }

    @Override
    public boolean changePassword(ChangePassRequest changePassRequest, String authHeader) throws AuthenticationException {

        if(!jwtTokenProvider.isTokenExpired(authHeader.substring(7)))
        {
            String username = jwtTokenProvider.getUsernameFromToken(authHeader.substring(7));
            Optional<User> userOptional = userRepository.findByUsername(username);
            if(userOptional.isPresent())
            {
                User user = userOptional.get();

                if(passwordEncoder.matches(changePassRequest.getOldPass(), user.getPassword()) &&
                        changePassRequest.getNewPass().matches(changePassRequest.getNewPassReconfirm())
                )
                {
                    user.setPassword(passwordEncoder.encode(changePassRequest.getNewPass()));
                    userRepository.save(user);
                    return true;
                }
                else
                    throw new AuthenticationException(PASSWORDMISMATCH);
            }
            else
                throw new UsernameNotFoundException(USERNOTFOUND);
        }
        else
            throw new RequestRejectedException(TOKENEXPIRED);

    }
}
