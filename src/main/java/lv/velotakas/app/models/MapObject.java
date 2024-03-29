package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.awt.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class MapObject {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(nullable = false, length = 300)
    private String type;

    @Column(nullable = false)
    private Point location;

    @Column(length = 300)
    private String description;

    @OneToMany(mappedBy = "mapObject")
    private Set<MapObjectRating> mapObjectRatings;

    @OneToMany(mappedBy = "mapObject")
    private  Set<TrailObject> trailObjects;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
