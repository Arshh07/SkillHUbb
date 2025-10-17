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
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserRepository userRepository;
    @Autowired private GigRepository gigRepository;
    @Autowired private MessageRepository messageRepository;

    @GetMapping
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/view-users";
    }

    @GetMapping("/gigs")
    public String viewGigs(Model model) {
        model.addAttribute("gigs", gigRepository.findAll());
        return "admin/view-gigs";
    }

    @GetMapping("/messages")
    public String viewMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "admin/view-messages";
    }
}