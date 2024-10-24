package com.example.demo.service;

import com.example.demo.entity.Job;

import java.util.List;

public interface JobService {

    Job findById(String id);

    List<Job> findAll();

    Job create(Job job);

    boolean delete(String id);
}
