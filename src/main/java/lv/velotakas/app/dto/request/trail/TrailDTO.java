package lv.velotakas.app.dto.request.trail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.velotakas.app.models.TrailObject;
import lv.velotakas.app.models.TrailRating;
import lv.velotakas.app.models.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailDTO {
    private Integer id;
    private String name;
    private String surface;
    private Integer complexity;
    private String comment;
    private User user;
    private Set<TrailRating> trailRatings;
    private Set<TrailObject> trailObjects;
}