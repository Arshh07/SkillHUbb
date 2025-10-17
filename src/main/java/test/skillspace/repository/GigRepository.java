package test.skillspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.skillspace.model.Gig;
import java.util.List;

public interface GigRepository extends JpaRepository<Gig, Long> {
    List<Gig> findByFreelancerId(Long freelancerId);

    // Method for searching gigs
    List<Gig> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleKeyword, String descriptionKeyword);
}