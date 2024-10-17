package com.example.demo.service;

import com.example.demo.storage.Worker;

import java.util.List;

public interface WorkerService {

    List<Worker> findAll();

    List<Worker> findByName(String name);

    Worker findById(int ID);

    Worker findByNameAndId(String name, int ID);

    Worker saveWorker(Worker worker);

    Worker updateWorker(Worker worker);

    void delete(int ID);


}
