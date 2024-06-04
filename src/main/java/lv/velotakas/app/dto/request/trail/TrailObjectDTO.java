package lv.velotakas.app.dto.request.trail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.velotakas.app.models.MapObject;
import lv.velotakas.app.models.Trail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailObjectDTO {
    private Integer trailId;
    private Trail trail;
    private MapObject mapObject;
}
