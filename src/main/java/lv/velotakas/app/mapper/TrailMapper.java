package lv.velotakas.app.mapper;
import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.dto.request.trail.UpdateTrailRequest;
import lv.velotakas.app.models.Trail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface TrailMapper {
    TrailDTO toDTO(Trail trail);
    Trail toEntity(TrailDTO trailDTO);

    void toUpdateRequest(UpdateTrailRequest updateTrailRequest, @MappingTarget Trail trail);
}
