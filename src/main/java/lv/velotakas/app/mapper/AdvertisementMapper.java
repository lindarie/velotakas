package lv.velotakas.app.mapper;

import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.dto.request.advertisement.AdvertisementUpdateDTO;
import lv.velotakas.app.dto.response.advertisement.AdvertisementResponseDTO;
import lv.velotakas.app.models.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {
    AdvertisementDTO toDto(Advertisement advertisement);
    Advertisement toEntity(AdvertisementDTO advertisementDTO);
    AdvertisementResponseDTO toResponseDto(Advertisement advertisement);

    void updateAdvertisement(AdvertisementUpdateDTO updateRequest, @MappingTarget Advertisement advertisement);
}
