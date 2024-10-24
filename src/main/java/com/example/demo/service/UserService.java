package com.example.demo.service;


import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    public User findById(String ID);

    public User findByUsername(String username);

    public List<User> findAll();

    public boolean login(User user);

    public User register(User user);

}
