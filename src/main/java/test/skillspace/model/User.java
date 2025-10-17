package test.skillspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List; // Import List

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    
    @Column(columnDefinition = "TEXT") // Add this
    private String bio;                 // Add this

    @OneToMany(mappedBy = "freelancer") // Add this relationship
    private List<Gig> gigs;             // A user can have many gigs
}