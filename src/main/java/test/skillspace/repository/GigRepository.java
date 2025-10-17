package test.skillspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.skillspace.model.Gig;
import java.util.List; // Import List

public interface GigRepository extends JpaRepository<Gig, Long> {
    // Add this method
    List<Gig> findByFreelancerId(Long freelancerId);
}