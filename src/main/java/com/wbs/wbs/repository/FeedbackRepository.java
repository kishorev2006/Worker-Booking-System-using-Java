package com.wbs.wbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbs.wbs.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByBookingUserId(Long userId);
    List<Feedback> findByBookingWorkerId(Long workerId);
}
