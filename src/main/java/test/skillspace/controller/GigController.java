package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.core.userdetails.UserDetails; // <<< REMOVED THIS UNUSED IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import test.skillspace.model.Gig;
import test.skillspace.model.User;
import test.skillspace.repository.GigRepository;
// import test.skillspace.repository.UserRepository; // <<< REMOVED THIS UNUSED IMPORT (and the field below)
import test.skillspace.security.CustomUserDetails; // Keep this import

import java.util.List;

@Controller
public class GigController {

    @Autowired
    private GigRepository gigRepository;

    // @Autowired // <<< REMOVED Autowired for userRepository
    // private UserRepository userRepository; // <<< REMOVED THIS UNUSED FIELD

    @GetMapping("/gigs/new")
    public String showCreateGigForm(Model model) {
        model.addAttribute("gig", new Gig());
        return "create-gig";
    }

    @PostMapping("/gigs/create")
    public String createGig(Gig gig, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User currentUser = userDetails.getUser(); // Get User directly
        gig.setFreelancer(currentUser);
        gigRepository.save(gig);
        return "redirect:/gigs/my-gigs";
    }

    @GetMapping("/gigs/my-gigs")
    public String showMyGigs(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User currentUser = userDetails.getUser(); // Get User directly
        List<Gig> myGigs = gigRepository.findByFreelancerId(currentUser.getId());
        model.addAttribute("gigs", myGigs);
        return "my-gigs";
    }

    @PostMapping("/gigs/delete/{id}")
    public String deleteGig(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User currentUser = userDetails.getUser(); // Get User directly
        Gig gig = gigRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // Check ownership using the currentUser obtained directly
        if (!gig.getFreelancer().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this gig.");
        }
        gigRepository.deleteById(id);
        return "redirect:/gigs/my-gigs";
    }
}