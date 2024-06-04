package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.dto.request.advertisement.AdvertisementUpdateDTO;
import lv.velotakas.app.dto.response.advertisement.AdvertisementResponseDTO;
import lv.velotakas.app.mapper.AdvertisementMapper;
import lv.velotakas.app.models.Advertisement;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.AdvertisementRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.AdvertisementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = advertisementMapper.toEntity(advertisementDTO);
        User user = userRepository.findByEmail(advertisementDTO.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        advertisement.setUser(user);
        advertisement.setTimeLimit(LocalDateTime.now().plusMonths(6).with(LocalTime.MAX));
        Advertisement savedAdvertisement = adRepository.save(advertisement);
        return advertisementMapper.toDto(savedAdvertisement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementDTO> getAllAdvertisements() {
        return adRepository.findAll().stream()
                .map(advertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementResponseDTO> getAdvertisementsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return adRepository.findAll().stream()
                    .map(advertisementMapper::toResponseDto)
                    .collect(Collectors.toList());
        } else {
            return adRepository.findByCategory(category).stream()
                    .map(advertisementMapper::toResponseDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AdvertisementResponseDTO getAdvertisementById(Integer id) {
        Optional<Advertisement> advertisementOptional = adRepository.findById(id);
        return advertisementOptional.map(advertisementMapper::toResponseDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean advertisementExistsById(Integer id) {
        return adRepository.existsById(id);
    }

    @Override
    @Transactional
    public void updateAdvertisement(AdvertisementUpdateDTO updateRequest, Integer id) {
        Advertisement advertisement = adRepository.findById(id).orElseThrow(() -> new RuntimeException("Advertisement not found"));
        advertisementMapper.updateAdvertisement(updateRequest, advertisement);
    }

    @Override
    @Transactional
    public void deleteAdvertisementById(Integer id) {
        adRepository.deleteById(id);
    }
}
