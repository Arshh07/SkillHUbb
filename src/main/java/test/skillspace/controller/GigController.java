package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import test.skillspace.model.Gig;
import test.skillspace.model.User;
import test.skillspace.repository.GigRepository;
import test.skillspace.repository.UserRepository;

import java.util.List;

@Controller
public class GigController {

    @Autowired
    private GigRepository gigRepository;

    @Autowired
    private UserRepository userRepository;

    // Show the form to create a new gig
    @GetMapping("/gigs/new")
    public String showCreateGigForm(Model model) {
        model.addAttribute("gig", new Gig());
        return "create-gig";
    }

    // Process the form submission for a new gig
    @PostMapping("/gigs/create")
    public String createGig(Gig gig, @AuthenticationPrincipal UserDetails userDetails) {
        // Find the currently logged-in user
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Associate the gig with the freelancer
        gig.setFreelancer(currentUser);
        gigRepository.save(gig);
        
        return "redirect:/gigs/my-gigs";
    }

    // Show the gigs for the currently logged-in freelancer
    @GetMapping("/gigs/my-gigs")
    public String showMyGigs(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Gig> myGigs = gigRepository.findByFreelancerId(currentUser.getId());
        model.addAttribute("gigs", myGigs);
        
        return "my-gigs";
    }
}