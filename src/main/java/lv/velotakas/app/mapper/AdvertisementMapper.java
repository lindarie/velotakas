package lv.velotakas.app.mapper;

import lv.velotakas.app.dto.request.advertisement.CreateAdvertisement;
import lv.velotakas.app.models.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "timeLimit", expression = "java(new java.sql.Time(new java.util.Date().getTime()))")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "user.role", constant = "AUTHENTICATED")
    Advertisement createAdvertisementToAdvertisement(CreateAdvertisement createAdvertisement, @MappingTarget Advertisement advertisement);
}
