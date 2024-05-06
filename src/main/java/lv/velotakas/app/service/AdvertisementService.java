package lv.velotakas.app.service;

import lv.velotakas.app.dto.request.advertisement.CreateAdvertisement;
import lv.velotakas.app.models.Advertisement;
import org.springframework.http.ResponseEntity;

public interface AdvertisementService {
    ResponseEntity<Advertisement> saveAdvertisement(CreateAdvertisement createAdvertisement);
}
