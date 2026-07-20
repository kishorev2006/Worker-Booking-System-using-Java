package com.wbs.wbs.controller;

import com.wbs.wbs.model.*;
import com.wbs.wbs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<Booking> allBookings = bookingService.getAllBookings();
        List<ServiceCategory> allServices = serviceCategoryService.getAllServices();
        model.addAttribute("bookings", allBookings);
        model.addAttribute("services", allServices);
        model.addAttribute("topRatedWorker", workerService.getTopRatedWorker());
        model.addAttribute("totalBookings", allBookings.size());
        model.addAttribute("totalFeedback", feedbackService.getAllFeedbacks().size());
        return "admin_dashboard";
    }

    // Service Category Management
    @GetMapping("/services")
    public String listServices(Model model) {
        List<ServiceCategory> services = serviceCategoryService.getAllServices();
        model.addAttribute("services", services);
        return "admin_services";
    }

    @GetMapping("/services/add")
    public String addServiceForm(Model model) {
        model.addAttribute("serviceCategory", new ServiceCategory());
        return "admin_service_form";
    }

    @PostMapping("/services/save")
    public String saveService(@ModelAttribute ServiceCategory serviceCategory) {
        serviceCategoryService.saveService(serviceCategory);
        return "redirect:/admin/services";
    }

    @GetMapping("/services/edit/{id}")
    public String editServiceForm(@PathVariable Long id, Model model) {
        ServiceCategory serviceCategory = serviceCategoryService.getServiceById(id);
        model.addAttribute("serviceCategory", serviceCategory);
        return "admin_service_form";
    }

    @GetMapping("/services/delete/{id}")
    public String deleteService(@PathVariable Long id) {
        serviceCategoryService.deleteService(id);
        return "redirect:/admin/services";
    }

    // Worker Management
    @GetMapping("/workers")
    public String listWorkers(Model model) {
        List<Worker> workers = workerService.getAllWorkers();
        model.addAttribute("workers", workers);
        return "admin_workers";
    }

    @GetMapping("/workers/add")
    public String addWorkerForm(Model model) {
        model.addAttribute("worker", new Worker());
        model.addAttribute("services", serviceCategoryService.getAllServices());
        return "admin_worker_form";
    }

    @PostMapping("/workers/save")
    public String saveWorker(@ModelAttribute Worker worker) {
        workerService.saveWorker(worker);
        return "redirect:/admin/workers";
    }

    @GetMapping("/workers/edit/{id}")
    public String editWorkerForm(@PathVariable Long id, Model model) {
        Optional<Worker> workerOpt = workerService.getWorkerById(id);
        if (workerOpt.isPresent()) {
            Worker worker = workerOpt.get();
            model.addAttribute("worker", worker);
            model.addAttribute("services", serviceCategoryService.getAllServices());
            
            List<Feedback> workerFeedbacks = feedbackService.getFeedbacksByWorkerId(id);
            double avgRating = workerFeedbacks.stream().mapToInt(Feedback::getRating).average().orElse(0.0);
            model.addAttribute("workerFeedbacks", workerFeedbacks);
            model.addAttribute("averageRating", avgRating);
            model.addAttribute("reviewCount", workerFeedbacks.size());
            
            return "admin_worker_form";
        }
        return "redirect:/admin/workers";
    }

    @GetMapping("/workers/delete/{id}")
    public String deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return "redirect:/admin/workers";
    }

    // Booking Management
    @GetMapping("/bookings")
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin_bookings";
    }

    @PostMapping("/bookings/assign")
    public String assignWorkerToBooking(@RequestParam Long bookingId, @RequestParam Long workerId) {
        Optional<Booking> bookingOpt = bookingService.findById(bookingId);
        Optional<Worker> workerOpt = workerService.getWorkerById(workerId);
        if (bookingOpt.isPresent() && workerOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setWorker(workerOpt.get());
            booking.setStatus("ASSIGNED");
            bookingService.saveBooking(booking);
        }
        return "redirect:/admin/bookings";
    }

    @GetMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/admin/bookings";
    }
}
