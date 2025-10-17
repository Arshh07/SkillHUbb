package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
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

    @GetMapping("/gigs/new")
    public String showCreateGigForm(Model model) {
        model.addAttribute("gig", new Gig());
        return "create-gig";
    }

    @PostMapping("/gigs/create")
    public String createGig(Gig gig, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        gig.setFreelancer(currentUser);
        // Logic for image URL removed.
        gigRepository.save(gig);
        return "redirect:/gigs/my-gigs";
    }

    @GetMapping("/gigs/my-gigs")
    public String showMyGigs(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Gig> myGigs = gigRepository.findByFreelancerId(currentUser.getId());
        model.addAttribute("gigs", myGigs);
        return "my-gigs";
    }

    @PostMapping("/gigs/delete/{id}")
    public String deleteGig(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Gig gig = gigRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!gig.getFreelancer().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this gig.");
        }
        gigRepository.deleteById(id);
        return "redirect:/gigs/my-gigs";
    }
}