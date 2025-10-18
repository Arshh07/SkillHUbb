package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Import this
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Import this
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody; // Import this
import test.skillspace.model.User;
import test.skillspace.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired // Add this Autowired field
    private PasswordEncoder passwordEncoder;

    // --- Add this temporary method ---
    @GetMapping("/generate-hash/{password}")
    @ResponseBody // Returns the hash directly as text
    public String generateHash(@PathVariable String password) {
        return "Hash for '" + password + "': " + passwordEncoder.encode(password);
    }
    // --- End of temporary method ---

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(User user) {
        userService.saveUser(user);
        return "redirect:/login?success";
    }
}