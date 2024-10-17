package com.example.demo.service;

import com.example.demo.storage.Worker;
import com.example.demo.storage.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WorkerServiceImpl implements WorkerService
{
    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    @Override
    public List<Worker> findByName(String name) {
        return workerRepository.findByName(name);
    }

    @Override
    public Worker findById(int ID) {
        Worker worker = null;
        Optional<Worker> result = workerRepository.findById(ID);
        if (result.isPresent())
            return result.get();
        else
            throw new RuntimeException("Worker under ID:" + ID + " doesn't exists");
    }

    @Override
    public Worker findByNameAndId(String name, int ID) {
        return workerRepository.findByNameAndId(name, ID);
    }

    @Override
    public Worker saveWorker(Worker worker) {
        int id;

        // MongoDB can make ID itself if we don't save any ID while saving the Object
        // But below code is used to generate own ID just for testing purposes
        do {
            Random random = new Random();
            id = random.nextInt(90000000) + 10000000;
        } while(workerRepository.findById(id).isPresent());

        worker.setId(id);
        return workerRepository.save(worker);
    }

    public Worker updateWorker(Worker worker) {
        Optional<Worker> updWorker = workerRepository.findById(worker.getId());
        if(updWorker.isPresent())
            return workerRepository.save(worker);
        else
            throw new RuntimeException("Worker with ID: "+worker.getId()+" was not found");
    }

    @Override
    public void delete(int ID) {
        workerRepository.deleteById(ID);
    }
}
