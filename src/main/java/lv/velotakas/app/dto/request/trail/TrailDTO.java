package lv.velotakas.app.dto.request.trail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.models.TrailObject;
import lv.velotakas.app.models.TrailRating;

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
    private String userEmail;
    private UserDAO user;
    private Set<TrailRating> trailRatings;
    private Set<TrailObject> trailObjects;
}