package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/login")
//    public boolean login(@RequestBody User user)
//    {
//        return true;
//    }
//
//    @PostMapping("/register")
//    public User register(@RequestBody User user)
//    {
//        return userService.register(user);
//    }
//

}
