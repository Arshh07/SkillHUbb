package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import test.skillspace.model.Gig;
import test.skillspace.repository.GigRepository;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private GigRepository gigRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Gig> gigs = gigRepository.findAll();
        model.addAttribute("gigs", gigs);
        return "dashboard";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/pay/{gigId}")
    public String showPaymentPage(@PathVariable Long gigId, Model model) {
        Gig gig = gigRepository.findById(gigId).orElse(null);
        model.addAttribute("gig", gig);
        return "payment";
    }
}