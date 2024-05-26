package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.awt.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Service {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column
    private Point location;

    @Column(nullable = false, length = 300)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 1000)
    private String filePath;

    @OneToMany(mappedBy = "service")
    private Set<ServiceRating> serviceRatings;
}
