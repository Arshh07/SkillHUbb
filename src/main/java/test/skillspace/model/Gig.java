package test.skillspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "gigs")
@Getter
@Setter
public class Gig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private User freelancer;
}