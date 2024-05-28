package lv.velotakas.app.dto.request.object;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.velotakas.app.models.MapObjectRating;
import lv.velotakas.app.models.TrailObject;
import lv.velotakas.app.models.User;


import java.awt.*;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectDTO {
    private Integer id;
    private String name;
    private String type;
    private Point location;
    private String description;
    private Set<MapObjectRating> mapObjectRatings;
    private Set<TrailObject> trailObjects;
    private User user;
}
