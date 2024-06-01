package lv.velotakas.app.dto.request.trail;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.velotakas.app.models.MapObject;
import lv.velotakas.app.models.Trail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailObjectDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trailId;

    private Trail trail;
    private MapObject mapObject;
}
