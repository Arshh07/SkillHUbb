package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import test.skillspace.repository.GigRepository;
import test.skillspace.repository.MessageRepository;
import test.skillspace.repository.UserRepository;

@Controller
@RequestMapping("/admin") // Base path for all admin routes
public class AdminController {

    @Autowired private UserRepository userRepository;
    @Autowired private GigRepository gigRepository;
    @Autowired private MessageRepository messageRepository;

    @GetMapping // Maps to /admin
    public String adminDashboard() {
        // Just returns the template name
        return "admin/dashboard";
    }

    @GetMapping("/users") // Maps to /admin/users
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/view-users";
    }

    @GetMapping("/gigs") // Maps to /admin/gigs
    public String viewGigs(Model model) {
        model.addAttribute("gigs", gigRepository.findAll());
        return "admin/view-gigs";
    }

    @GetMapping("/messages") // Maps to /admin/messages
    public String viewMessages(Model model) {
        // Fetch messages ordered by most recent first for better monitoring
        model.addAttribute("messages", messageRepository.findAllByOrderByCreatedAtDesc()); 
        return "admin/view-messages";
    }
}