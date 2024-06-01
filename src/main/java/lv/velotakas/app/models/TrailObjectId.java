package lv.velotakas.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TrailObjectId implements Serializable {

    @Column(name = "trail_id")
    private Integer trailId;

    @Column(name = "object_id")
    private Integer objectId;
}
