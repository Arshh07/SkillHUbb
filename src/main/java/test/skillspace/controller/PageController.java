package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import test.skillspace.model.Gig;
import test.skillspace.model.Review;
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
    public String showDashboard(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Gig> gigs;
        if (keyword != null && !keyword.isEmpty()) {
            gigs = gigRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
            model.addAttribute("searchKeyword", keyword);
        } else {
            gigs = gigRepository.findAll();
        }
        model.addAttribute("gigs", gigs);
        return "dashboard";
    }

    @GetMapping("/gigs/{gigId}")
    public String showGigDetail(@PathVariable Long gigId, Model model) {
        Gig gig = gigRepository.findById(gigId).orElseThrow(() -> new RuntimeException("Gig not found"));
        model.addAttribute("gig", gig);
        model.addAttribute("newReview", new Review());
        return "gig-detail";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }
}