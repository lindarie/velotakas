package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Time;

@Data
@NoArgsConstructor
@Entity
public class Advertisement {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 300)
    private String category;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Time timeLimit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "advertisement")
    private Payment payment;
}
