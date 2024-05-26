package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.dto.response.advertisement.AdvertisementResponseDTO;
import lv.velotakas.app.mapper.AdvertisementMapper;
import lv.velotakas.app.models.Advertisement;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.AdvertisementRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.AdvertisementService;
import org.springframework.stereotype.Service;

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
    public AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementDTO) {
        Advertisement advertisement = advertisementMapper.toEntity(advertisementDTO);
        User user = userRepository.findByEmail(advertisementDTO.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        advertisement.setUser(user);
        advertisement.setTimeLimit(LocalDateTime.now().plusMonths(6).with(LocalTime.MAX));
        Advertisement savedAdvertisement = adRepository.save(advertisement);
        return advertisementMapper.toDto(savedAdvertisement);
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        return adRepository.findAll().stream()
                .map(advertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementResponseDTO> getAdvertisementsByCategory(String category) {
        return adRepository.findByCategory(category).stream()
                .map(advertisementMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdvertisementResponseDTO getAdvertisementById(Integer id) {
        Optional<Advertisement> advertisementOptional = adRepository.findById(id);
        return advertisementOptional.map(advertisementMapper::toResponseDto).orElse(null);
    }
}
