package test.skillspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import test.skillspace.model.Gig;
import test.skillspace.model.Review;
import test.skillspace.model.User;
import test.skillspace.repository.GigRepository;
import test.skillspace.repository.ReviewRepository;
import test.skillspace.security.CustomUserDetails;

@Controller
public class ReviewController {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private GigRepository gigRepository;

    @PostMapping("/gigs/{gigId}/reviews")
    public String addReview(@PathVariable Long gigId, Review review, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User currentUser = userDetails.getUser();
        Gig gig = gigRepository.findById(gigId).orElseThrow(() -> new RuntimeException("Gig not found"));

        review.setClient(currentUser);
        review.setGig(gig);
        reviewRepository.save(review);

        // Redirect back to the gig detail page after submitting
        return "redirect:/gigs/" + gigId;
    }
}