package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.core.userdetails.UserDetails; // <<< REMOVED THIS UNUSED IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import test.skillspace.model.User;
import test.skillspace.repository.UserRepository;
import test.skillspace.security.CustomUserDetails; // Keep this import

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) { // Use CustomUserDetails
        User currentUser = userDetails.getUser(); // Get User from CustomUserDetails
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(User user, @AuthenticationPrincipal CustomUserDetails userDetails) { // Use CustomUserDetails
        User currentUser = userDetails.getUser(); // Get User from CustomUserDetails

        currentUser.setBio(user.getBio());
        // Image logic removed

        userRepository.save(currentUser);
        return "redirect:/profile?success";
    }
}