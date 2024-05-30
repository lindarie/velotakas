package lv.velotakas.app.mapper;
import lv.velotakas.app.dto.request.object.ObjectDTO;
import lv.velotakas.app.dto.request.object.UpdateObjectRequest;
import lv.velotakas.app.models.MapObject;
import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ObjectMapper {
    ObjectDTO toDTO(MapObject object);
    MapObject toEntity(ObjectDTO objectDTO);

    void toUpdateRequest(UpdateObjectRequest updateObjectRequest, @MappingTarget MapObject mapObject);

}
