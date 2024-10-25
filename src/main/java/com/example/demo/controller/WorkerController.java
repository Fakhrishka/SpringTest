package com.example.demo.controller;

import com.example.demo.entity.Job;
import com.example.demo.service.WorkerService;
import com.example.demo.entity.Worker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    // GET Requests :

//    @PreAuthorize("hasRole('ADMIN')") == @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/worker")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(workerService.findAll());

    }


    @GetMapping("/worker/{workerId}")
    public ResponseEntity<?> findById(@PathVariable String workerId) {
        return ResponseEntity.ok(workerService.findById(workerId));
    }

    @GetMapping("/worker/findbynameandid/{workerName}&{workerID}")
    public ResponseEntity<?> findByNameAndId(@PathVariable String workerName, @PathVariable String workerID) {
        return ResponseEntity.ok(workerService.findByNameAndId(workerName, workerID));
    }


    // POST Requests
    @PostMapping("/worker")
    public ResponseEntity<?> createWorker(@RequestBody Worker worker) {
        return ResponseEntity.ok(workerService.saveWorker(worker));
    }

    // DELETE Requests
    @DeleteMapping("/worker/{workerID}")
    public ResponseEntity<?> deleteWorker(@PathVariable String workerID) {
        return ResponseEntity.ok(workerService.delete(workerID));
    }

    @PutMapping("/addjob")
    public ResponseEntity<?> addWorkerJob(@Valid @RequestBody Job job, @Valid @RequestBody String workerID) {
        return ResponseEntity.ok(workerService.addWorkerJob(job, workerID));
    }

    // PUT Requests
    @PutMapping("/worker")
    public ResponseEntity<?> updateWorker(@RequestBody Worker worker) {
        return ResponseEntity.ok(workerService.updateWorker(worker));
    }
}