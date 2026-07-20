package com.wbs.wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbs.wbs.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
