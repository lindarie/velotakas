package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.mapper.AdvertisementMapper;
import lv.velotakas.app.models.Advertisement;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.AdvertisementRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.AdvertisementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository adRepository;
    private final AdvertisementMapper advertisementMapper;
    private final UserRepository userRepository;

    public AdvertisementServiceImpl(AdvertisementRepository adRepository, AdvertisementMapper advertisementMapper, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.advertisementMapper = advertisementMapper;
        this.userRepository = userRepository;
    }

    public AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = advertisementMapper.toEntity(advertisementDTO);
        User user = userRepository.findByEmail(advertisementDTO.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        advertisement.setUser(user);
        advertisement.setTimeLimit(LocalDateTime.now().plusMonths(6).with(LocalTime.MAX));
        Advertisement savedAdvertisement = adRepository.save(advertisement);
        return advertisementMapper.toDto(savedAdvertisement);
    }
}
