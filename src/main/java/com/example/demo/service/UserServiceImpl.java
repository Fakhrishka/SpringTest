package com.example.demo.service;

import com.example.demo.entity.Job;
import com.example.demo.entity.User;
import com.example.demo.entity.Worker;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(String ID) {
        Optional<User> result = userRepository.findById(ID);
        if(result.isPresent())
            return result.get();
        else
            throw new RuntimeException("User not found");
    }


    @Override
    public User findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        if(result.isPresent())
            return result.get();
        else
            throw new RuntimeException("User not found");
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean login(User user)
    {
        Optional<User> result = userRepository.findByUsername(user.getUsername());
        if(result.isPresent())
            return passwordEncoder.matches(user.password, result.get().password);
        else
            throw new RuntimeException("There is NO USER with such Username!");
    }

    @Override
    public User register(User user)
    {
        Optional<User> result = userRepository.findByUsername(user.getUsername());
        if(result.isPresent())
            throw new RuntimeException("User with such Username already exists!");
        else
        {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
    }
}
