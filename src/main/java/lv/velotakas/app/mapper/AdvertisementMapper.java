package lv.velotakas.app.mapper;

import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.models.Advertisement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {
    AdvertisementDTO toDto(Advertisement advertisement);
    Advertisement toEntity(AdvertisementDTO advertisementDTO);
}
