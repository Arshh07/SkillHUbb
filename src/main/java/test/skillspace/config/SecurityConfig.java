package test.skillspace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Assuming still plain text for now
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import test.skillspace.repository.UserRepository;
import test.skillspace.security.CustomUserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Using plain text encoder as per previous step
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Admin pages require ADMIN role
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                // Gig creation/management requires FREELANCER role
                .requestMatchers("/gigs/new", "/gigs/my-gigs", "/gigs/create", "/gigs/delete/**").hasAuthority("FREELANCER")
                 // Submitting reviews requires CLIENT role (optional, but good practice)
                .requestMatchers("/gigs/*/reviews").hasAuthority("CLIENT")
                // Allow CSS, public pages without login
                .requestMatchers("/css/**", "/register", "/about", "/login").permitAll()
                // Any other request (like viewing /dashboard, /gigs/{id}, /profile, /messages/**) requires the user to be logged in (authenticated)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}