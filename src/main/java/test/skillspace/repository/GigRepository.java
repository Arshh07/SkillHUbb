package test.skillspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.skillspace.model.Gig;

public interface GigRepository extends JpaRepository<Gig, Long> {}