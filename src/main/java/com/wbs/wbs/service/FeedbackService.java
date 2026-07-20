package com.wbs.wbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbs.wbs.model.Feedback;
import com.wbs.wbs.repository.FeedbackRepository;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbacksByUserId(Long userId) {
        return feedbackRepository.findByBookingUserId(userId);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbacksByWorkerId(Long workerId) {
        return feedbackRepository.findByBookingWorkerId(workerId);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}
