package com.example.demo.rest;

import com.example.demo.service.WorkerService;
import com.example.demo.storage.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    // GET Requests :
    @GetMapping("/worker")
    public List<Worker> findAll()
    {
        return workerService.findAll();
    }
    @GetMapping("/worker/{workerId}")
    public Worker findById(@PathVariable int workerId)
    {
        return workerService.findById(workerId);
    }
    @GetMapping("/worker/findbynameandid/{workerName}&{workerID}")
    public Worker findByNameAndId(@PathVariable String workerName, @PathVariable int workerID)
    {
        return workerService.findByNameAndId(workerName, workerID);
    }

    // POST Requests
    @PostMapping("/worker")
    public Worker createWorker(@RequestBody Worker worker)
    {
        return workerService.saveWorker(worker);
    }

    // DELETE Requests
    @DeleteMapping("/worker/{workerID}")
    public void deleteWorker(@PathVariable int workerID)
    {
        workerService.delete(workerID);
    }

    // PUT Requests
    @PutMapping("/worker")
    public Worker updateWorker(@RequestBody Worker worker)
    {
        return workerService.updateWorker(worker);
    }
}
