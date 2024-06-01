package lv.velotakas.app.mapper;
import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.dto.request.trail.TrailObjectDTO;
import lv.velotakas.app.dto.request.trail.UpdateTrailRequest;
import lv.velotakas.app.models.Trail;
import lv.velotakas.app.models.TrailObject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface TrailMapper {
    TrailDTO toDTO(Trail trail);
    Trail toEntity(TrailDTO trailDTO);

    void toUpdateRequest(UpdateTrailRequest updateTrailRequest, @MappingTarget Trail trail);

    TrailObjectDTO toTrailObjectDTO(TrailObject trailObject);
    TrailObject toTrailObjectEntity(Integer ObjectId, Integer TrailId);

}
