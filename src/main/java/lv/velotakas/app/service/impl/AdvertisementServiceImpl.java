package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.advertisement.CreateAdvertisement;
import lv.velotakas.app.mapper.AdvertisementMapper;
import lv.velotakas.app.models.Advertisement;
import lv.velotakas.app.repositories.AdvertisementRepository;
import lv.velotakas.app.service.AdvertisementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository adRepository;
    private final AdvertisementMapper advertisementMapper;

    public AdvertisementServiceImpl(AdvertisementRepository adRepository, AdvertisementMapper advertisementMapper) {
        this.adRepository = adRepository;
        this.advertisementMapper = advertisementMapper;
    }

    public ResponseEntity<Advertisement> saveAdvertisement(CreateAdvertisement createAdvertisement) {
        Advertisement advertisement = advertisementMapper.createAdvertisementToAdvertisement(createAdvertisement, new Advertisement());
        return ResponseEntity.ok(adRepository.save(advertisement));
    }
}
