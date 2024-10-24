package com.example.demo.service;

import com.example.demo.entity.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job findById(String id)
    {
        Optional<Job> qResult = jobRepository.findById(id);
        if(qResult.isPresent())
            return qResult.get();
        else
            throw new RuntimeException("No Job found under ID: " + id);
    }

    public Job create(Job job)
    {
        return jobRepository.insert(job);
    }

    public List<Job> findAll()
    {
        return jobRepository.findAll();
    }

    public boolean delete(String id)
    {
        Job jobDetails = this.findById(id);
        jobRepository.delete(jobDetails);
        return true;
    }
}
