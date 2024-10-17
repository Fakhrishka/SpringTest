package com.example.demo.service;


import com.example.demo.storage.User;

import java.util.List;

public interface UserService {

    public User findById(int ID);

    public User findByUserName(String username);

    public List<User> findAll();

    public boolean login(User user);

    public User register(User user);

}
