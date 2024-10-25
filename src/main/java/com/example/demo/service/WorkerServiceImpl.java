package com.example.demo.service;

import com.example.demo.entity.Job;
import com.example.demo.entity.Worker;
import com.example.demo.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl implements WorkerService
{
    private final static String WORKERNOTFOUND = "Worker with such name or ID not found";
//    private final static String

    @Autowired
    private WorkerRepository workerRepository;

//    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    // check how to combine above and below methods
    @Override
    public List<Worker> findByName(String name) {
        List<Worker> workersList = workerRepository.findByName(name);
        if(workersList.isEmpty())
            throw new IllegalArgumentException(WORKERNOTFOUND);
        else
            return workersList;
    }

    @Override
    public Worker findById(String ID) {
        Optional<Worker> result = workerRepository.findById(ID);
        if (result.isPresent())
            return result.get();
        else
            throw new IllegalArgumentException(WORKERNOTFOUND);
    }

    @Override
    public List<Job> getWorkerJobs(Worker worker)
    {
        Optional<Worker> result = workerRepository.findById(worker.getId());
        if(result.isPresent())
            return worker.getJobs();
        else
            throw new IllegalArgumentException(WORKERNOTFOUND);
    }

    @Override
    public List<Job> addWorkerJob(Job job, String workerId)
    {
        Worker worker = this.findById(workerId);
        if(this.isWorkerValid(worker))
        {
            List<Job> jobs = worker.getJobs();
            jobs.add(job);
            worker.setJobs(jobs);
            workerRepository.save(worker);
            return jobs;
        }
        else
            throw new IllegalArgumentException(WORKERNOTFOUND);
    }

    @Override
    public Worker findByNameAndId(String name, String ID) {
        Worker worker = workerRepository.findByNameAndId(name, ID);
        if(this.isWorkerValid(worker))
            return worker;
        else
            throw new IllegalArgumentException(WORKERNOTFOUND);
        // add exception later
    }

    @Override
    public Worker saveWorker(Worker worker) {
        Worker newWorker = workerRepository.insert(worker);
        if(this.isWorkerValid(newWorker))
            return newWorker;
        else
            throw new IllegalArgumentException(WORKERNOTFOUND);

        //Customs ID implementation below :
//        int id;
//
//        // MongoDB can make ID itself if we don't save any ID while saving the Object
//        // But below code is used to generate own ID just for testing purposes
//        do {
//            Random random = new Random();
//            id = random.nextInt(90000000) + 10000000;
//        } while(workerRepository.findById(id).isPresent());
//
//        worker.setId(id);
    }

    public Worker updateWorker(Worker worker) {
        Optional<Worker> updWorker = workerRepository.findById(worker.getId());
        if(updWorker.isPresent())
            return workerRepository.save(worker);
        else
            throw new IllegalArgumentException(WORKERNOTFOUND);
    }

    @Override
    public boolean delete(String ID) {
        Worker workerDetails = this.findById(ID);
        workerRepository.delete(workerDetails);
        return true;
    }

    @Override
    public boolean isWorkerValid(Worker worker)
    {
        if(!worker.getId().isEmpty())
        {
             Optional<Worker> qResult = workerRepository.findById(worker.getId());
             if(qResult.isPresent())
                 return true;
        }
        else if (!worker.getName().isEmpty())
        {
            List<Worker> qResult = workerRepository.findByName(worker.getName());
            if(!qResult.isEmpty())
                return true;
        }
        return false;
    }
}
