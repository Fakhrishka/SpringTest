package com.example.demo.responses;

import com.example.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponse {

    String id;
    String username;
    List<Role> roles;
}
