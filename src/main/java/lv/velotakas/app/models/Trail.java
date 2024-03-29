package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Trail {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(nullable = false, length = 40)
    private String surface;

    private int complexity;

    @Column(length = 300)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "trail")
    private Set<TrailRating> trailRatings;

    @OneToMany(mappedBy = "trail")
    private Set<TrailObject> trailObjects;
}
