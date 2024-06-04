package lv.velotakas.app.service;

import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.dto.request.advertisement.AdvertisementUpdateDTO;
import lv.velotakas.app.dto.response.advertisement.AdvertisementResponseDTO;

import java.util.List;

public interface AdvertisementService {
    AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO);
    public List<AdvertisementDTO> getAllAdvertisements();
    List<AdvertisementResponseDTO> getAdvertisementsByCategory(String category);
    AdvertisementResponseDTO getAdvertisementById(Integer id);

    boolean advertisementExistsById(Integer id);

    void updateAdvertisement(AdvertisementUpdateDTO updateRequest, Integer id);

    void deleteAdvertisementById(Integer id);
}
