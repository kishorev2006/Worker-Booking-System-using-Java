package com.wbs.wbs.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wbs.wbs.model.User;
import com.wbs.wbs.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }

    @GetMapping("/admin/login")
    public String showAdminLoginForm() {
        return "admin_login";
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam String username, @RequestParam String password,
                             HttpSession session, Model model) {
        Optional<User> admin = userService.authenticate(username, password, "ADMIN");
        if (admin.isPresent()) {
            session.setAttribute("user", admin.get());
            return "redirect:/admin/dashboard";
        }

        Optional<User> account = userService.findByUsername(username);
        if (account.isPresent() && account.get().getPassword().equals(password)
                && "USER".equalsIgnoreCase(account.get().getRole())) {
            model.addAttribute("error", "Please use the User Login page.");
        } else {
            model.addAttribute("error", "Invalid username or password");
        }
        return "admin_login";
    }
}
