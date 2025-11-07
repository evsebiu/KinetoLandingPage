package com.example.KinetoWebsite.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "AdminDashboard"; // Looks for AdminDashboard.html
    }

    @GetMapping("/admin/services")
    public String serviceManagement() {
        return "ServiceManagement"; // Looks for ServiceManagement.html
    }

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "AdminLogin"; // Looks for AdminLogin.html
    }
}