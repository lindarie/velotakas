package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Bicycle {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 30)
    private String brand;

    @Column(length = 30)
    private String model;

    @Column(length = 30)
    private String type;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
