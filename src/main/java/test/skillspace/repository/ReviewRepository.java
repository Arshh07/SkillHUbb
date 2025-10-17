package test.skillspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.skillspace.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {}