package com.example.demo.service;

import com.example.demo.entity.Job;
import com.example.demo.entity.Worker;

import java.util.List;

public interface WorkerService {

    List<Worker> findAll();

    List<Worker> findByName(String name);

    Worker findById(String ID);

//    List<Job> addWorkerJob(Job job);

    List<Job> addWorkerJob(Job job, String workerId);

    Worker findByNameAndId(String name, String ID);

    List<Job> getWorkerJobs(Worker worker);

    Worker saveWorker(Worker worker);

    Worker updateWorker(Worker worker);

    boolean delete(String ID);

    boolean isWorkerValid(Worker worker);
}
