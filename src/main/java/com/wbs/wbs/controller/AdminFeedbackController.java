package com.wbs.wbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wbs.wbs.model.Feedback;
import com.wbs.wbs.service.FeedbackService;

@Controller
@RequestMapping("/admin")
public class AdminFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedbacks")
    public String listFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "admin_feedbacks";
    }

    @GetMapping("/feedbacks/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/admin/feedbacks";
    }
}
