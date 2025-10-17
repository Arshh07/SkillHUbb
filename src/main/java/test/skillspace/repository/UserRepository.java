package test.skillspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.skillspace.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}