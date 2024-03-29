package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String method;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "advertisement_id", nullable = false)
    private Advertisement advertisement;
}
