package lv.velotakas.app.mapper;

import lv.velotakas.app.dto.request.service.ServiceRequest;
import lv.velotakas.app.dto.request.service.ServiceUpdateRequest;
import lv.velotakas.app.dto.response.ServiceDTO;
import lv.velotakas.app.models.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BikeServiceMapper {

    @Mapping(target="name", source = "name")
    @Mapping(target="comment", source = "comment")
    @Mapping(target="filePath", source = "filePath")
    Service toBikeService(ServiceRequest request);

    @Mapping(target="name", source = "name")
    @Mapping(target="comment", source = "comment")
    @Mapping(target="filePath", source = "filePath")
    void updateService(ServiceUpdateRequest updateRequest, @MappingTarget Service service);

    @Mapping(target="name", source = "name")
    @Mapping(target="comment", source = "comment")
    @Mapping(target="filePath", source = "filePath")
    ServiceDTO toServiceDTO(Service service);
}
