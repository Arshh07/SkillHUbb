package test.skillspace.service;

import org.springframework.beans.factory.annotation.Autowired;
// PasswordEncoder import is no longer needed if you remove hashing
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.skillspace.model.User;
import test.skillspace.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // PasswordEncoder field is removed
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        // --- REMOVED HASHING STEP ---
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        // --- END OF CHANGE ---

        // Role is set from the form
        return userRepository.save(user);
    }
}