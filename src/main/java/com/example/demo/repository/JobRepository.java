package com.example.demo.repository;

import com.example.demo.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {

//    Job getById(String id);

//    boolean delete(String id);
}
