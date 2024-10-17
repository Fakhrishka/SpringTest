package com.example.demo.storage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WorkerRepository extends MongoRepository<Worker, Integer> {

    List<Worker> findByName(String name);

    @Query("{ 'name' : ?0}, { 'id' : ?1")
    Worker findByNameAndId(String name, int id);
}
