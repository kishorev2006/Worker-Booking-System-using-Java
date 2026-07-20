package com.wbs.wbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbs.wbs.model.Worker;
import com.wbs.wbs.repository.WorkerRepository;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    public List<Worker> getWorkersByServiceCategory(Long serviceCategoryId) {
        return workerRepository.findAll().stream()
                .filter(worker -> worker.getServiceCategory().getId().equals(serviceCategoryId))
                .toList();
    }

    public Optional<Worker> getWorkerById(Long id) {
        return workerRepository.findById(id);
    }

    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getTopRatedWorker() {
        return workerRepository.findAll().stream()
                .max((w1, w2) -> Double.compare(
                        w1.getRating() == null ? 0 : w1.getRating(),
                        w2.getRating() == null ? 0 : w2.getRating()))
                .orElse(null);
    }
}
