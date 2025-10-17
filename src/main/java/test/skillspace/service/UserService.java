package test.skillspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.skillspace.model.User;
import test.skillspace.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // The role is now set from the registration form, so we no longer set it here.
        // user.setRole("FREELANCER"); // <-- REMOVE THIS LINE
        return userRepository.save(user);
    }
}