package com.wbs.wbs.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wbs.wbs.model.Booking;
import com.wbs.wbs.model.Feedback;
import com.wbs.wbs.model.ServiceCategory;
import com.wbs.wbs.model.User;
import com.wbs.wbs.model.Worker;
import com.wbs.wbs.service.BookingService;
import com.wbs.wbs.service.FeedbackService;
import com.wbs.wbs.service.ServiceCategoryService;
import com.wbs.wbs.service.UserService;
import com.wbs.wbs.service.WorkerService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        user.setRole("USER");
        userService.registerUser(user);
        model.addAttribute("message", "Registration successful. Please login.");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            session.setAttribute("user", user);
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        List<ServiceCategory> services = serviceCategoryService.getAllServices();
        model.addAttribute("services", services);
        return "user_dashboard";
    }

    @GetMapping("/workers")
    public String getWorkersByService(@RequestParam Long serviceId, Model model) {
        List<Worker> workers = workerService.getWorkersByServiceCategory(serviceId);
        model.addAttribute("workers", workers);
        return "workers_list";
    }

    @GetMapping("/book")
    public String showBookingForm(@RequestParam Long workerId, Model model) {
        Optional<Worker> workerOpt = workerService.getWorkerById(workerId);
        if (workerOpt.isEmpty()) {
            model.addAttribute("error", "Worker not found");
            return "user_dashboard";
        }
        model.addAttribute("worker", workerOpt.get());
        return "booking_form";
    }

    @PostMapping("/book")
    public String bookWorker(@RequestParam Long workerId,
                             @RequestParam String date,
                             @RequestParam String timeSlot,
                             @RequestParam Integer duration,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        LocalDate bookingDate = LocalDate.parse(date);
        LocalTime bookingTime = LocalTime.parse(timeSlot);

        // Check if worker is already booked for the selected time slot
        if (!bookingService.isWorkerAvailable(workerId, bookingDate, bookingTime, duration)) {
            model.addAttribute("error", "Worker is already booked for the selected time slot");
            Optional<Worker> workerOpt = workerService.getWorkerById(workerId);
            workerOpt.ifPresent(worker -> model.addAttribute("worker", worker));
            return "booking_form";
        }

        // Check if user already has a booking for the same time slot
        boolean userHasBooking = bookingService.isUserBookedForTimeSlot(user.getId(), bookingDate, bookingTime);
        if (userHasBooking) {
            model.addAttribute("error", "You already have a booking for the selected time slot");
            Optional<Worker> workerOpt = workerService.getWorkerById(workerId);
            workerOpt.ifPresent(worker -> model.addAttribute("worker", worker));
            return "booking_form";
        }

        Optional<Worker> workerOpt = workerService.getWorkerById(workerId);
        if (workerOpt.isEmpty()) {
            model.addAttribute("error", "Worker not found");
            return "booking_form";
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setWorker(workerOpt.get());
        booking.setServiceCategory(workerOpt.get().getServiceCategory());
        booking.setDate(bookingDate);
        booking.setTimeSlot(bookingTime);
        booking.setDuration(duration);
        booking.setStatus("PENDING");

        bookingService.saveBooking(booking);

        model.addAttribute("booking", booking);
        return "redirect:/user/payment?bookingId=" + booking.getId();
    }

    @GetMapping("/payment")
    public String paymentPage(@RequestParam Long bookingId, Model model) {
        Optional<Booking> bookingOpt = bookingService.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            model.addAttribute("error", "Booking not found");
            return "user_dashboard";
        }
        Booking booking = bookingOpt.get();
        double pricePerHour = 50.0; // Example fixed price per hour, can be dynamic
        double totalPrice = pricePerHour * booking.getDuration();

        model.addAttribute("booking", booking);
        model.addAttribute("totalPrice", totalPrice);
        return "payment";
    }

    @PostMapping("/payment/success")
    public String paymentSuccess(@RequestParam Long bookingId, Model model) {
        Optional<Booking> bookingOpt = bookingService.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            model.addAttribute("error", "Booking not found");
            return "user_dashboard";
        }
        Booking booking = bookingOpt.get();
        booking.setStatus("COMPLETED");
        bookingService.saveBooking(booking);

        model.addAttribute("message", "Payment Successful");
        return "payment_success";
    }

    @GetMapping("/bookings")
    public String bookingHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        List<Booking> bookings = bookingService.getBookingsByUserId(user.getId());
        model.addAttribute("bookings", bookings);
        return "booking_history";
    }

    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        // Update user details except username and role
        user.setPhoneNo(updatedUser.getPhoneNo());
        user.setAddress(updatedUser.getAddress());
        userService.saveUser(user);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        model.addAttribute("message", "Profile updated successfully");
        return "profile";
    }

    @GetMapping("/feedback")
    public String feedbackForm(@RequestParam Long bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        return "feedback";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam Long bookingId,
                                 @RequestParam Integer rating,
                                 @RequestParam String comments,
                                 Model model) {
        Optional<Booking> bookingOpt = bookingService.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            model.addAttribute("error", "Booking not found");
            return "booking_history";
        }
        Feedback feedback = new Feedback();
        feedback.setBooking(bookingOpt.get());
        feedback.setRating(rating);
        feedback.setComments(comments);
        feedback.setFeedbackDate(LocalDate.now());
        feedbackService.saveFeedback(feedback);

        model.addAttribute("message", "Feedback submitted successfully");
        return "redirect:/user/bookings";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
