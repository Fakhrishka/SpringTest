package com.example.demo.controller;

import com.example.demo.entity.Job;
import com.example.demo.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/jobs")
@RestController
public class JobController {

    @Autowired
    JobService jobService;

    @GetMapping
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id)
    {
        return ResponseEntity.ok(jobService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Job job)
    {
        return ResponseEntity.ok(jobService.create(job));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody String id)
    {
        return ResponseEntity.ok(jobService.delete(id));
    }
}
