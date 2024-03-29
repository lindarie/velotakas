package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class TrailObject {
    @EmbeddedId
    private TrailObjectId id;

    @ManyToOne
    @MapsId("trailId")
    @JoinColumn(name = "trail_id")
    private Trail trail;

    @ManyToOne
    @MapsId("objectId")
    @JoinColumn(name = "object_id")
    private MapObject mapObject;
}
