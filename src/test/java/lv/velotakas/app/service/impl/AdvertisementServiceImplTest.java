package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.dto.request.advertisement.AdvertisementUpdateDTO;
import lv.velotakas.app.dto.response.advertisement.AdvertisementResponseDTO;
import lv.velotakas.app.mapper.AdvertisementMapper;
import lv.velotakas.app.models.Advertisement;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.AdvertisementRepository;
import lv.velotakas.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceImplTest {

    @Mock
    private AdvertisementRepository adRepository;

    @Mock
    private AdvertisementMapper advertisementMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdvertisementServiceImpl advertisementService;

    private AdvertisementDTO advertisementDTO;
    private Advertisement advertisement;
    private User user;

    @BeforeEach
    void setUp() {
        advertisementDTO = new AdvertisementDTO("Electronics", "Description", new BigDecimal("100.00"), null, "path/to/file", "user@example.com");
        advertisement = new Advertisement();
        user = new User();
    }

    @Test
    void createAdvertisement_ShouldReturnCreatedAdvertisement() {
        when(advertisementMapper.toEntity(any(AdvertisementDTO.class))).thenReturn(advertisement);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(adRepository.save(any(Advertisement.class))).thenReturn(advertisement);
        when(advertisementMapper.toDto(any(Advertisement.class))).thenReturn(advertisementDTO);

        AdvertisementDTO result = advertisementService.createAdvertisement(advertisementDTO);

        assertThat(result).isNotNull();
        assertThat(result.getUserEmail()).isEqualTo("user@example.com");
        verify(adRepository).save(advertisement);
    }

    @Test
    void createAdvertisement_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> advertisementService.createAdvertisement(advertisementDTO));
        verify(adRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void getAllAdvertisements_ShouldReturnListOfAdvertisements() {
        when(adRepository.findAll()).thenReturn(Collections.singletonList(advertisement));
        when(advertisementMapper.toDto(any(Advertisement.class))).thenReturn(advertisementDTO);

        assertThat(advertisementService.getAllAdvertisements()).hasSize(1);
        verify(adRepository).findAll();
    }

    @Test
    void getAdvertisementsByCategory_ShouldReturnListOfAdvertisements() {
        when(adRepository.findByCategory(anyString())).thenReturn(Collections.singletonList(advertisement));
        when(advertisementMapper.toResponseDto(any(Advertisement.class))).thenReturn(new AdvertisementResponseDTO());

        assertThat(advertisementService.getAdvertisementsByCategory("Electronics")).hasSize(1);
        verify(adRepository).findByCategory(anyString());
    }

    @Test
    void getAdvertisementById_ShouldReturnAdvertisement() {
        when(adRepository.findById(anyInt())).thenReturn(Optional.of(advertisement));
        when(advertisementMapper.toResponseDto(any(Advertisement.class))).thenReturn(new AdvertisementResponseDTO());

        AdvertisementResponseDTO result = advertisementService.getAdvertisementById(1);

        assertThat(result).isNotNull();
        verify(adRepository).findById(anyInt());
    }

    @Test
    void getAdvertisementById_ShouldReturnNull_WhenAdvertisementNotFound() {
        when(adRepository.findById(anyInt())).thenReturn(Optional.empty());

        AdvertisementResponseDTO result = advertisementService.getAdvertisementById(1);

        assertThat(result).isNull();
        verify(adRepository).findById(anyInt());
    }

    @Test
    void advertisementExistsById_ShouldReturnTrue_WhenExists() {
        when(adRepository.existsById(anyInt())).thenReturn(true);

        assertThat(advertisementService.advertisementExistsById(1)).isTrue();
        verify(adRepository, times(1)).existsById(anyInt());
    }

    @Test
    void advertisementExistsById_ShouldReturnFalse_WhenNotExists() {
        when(adRepository.existsById(anyInt())).thenReturn(false);

        assertThat(advertisementService.advertisementExistsById(1)).isFalse();
        verify(adRepository).existsById(anyInt());
    }

    @Test
    void updateAdvertisement_ShouldUpdateAdvertisement() {
        AdvertisementUpdateDTO updateDTO = new AdvertisementUpdateDTO("Electronics", "Updated Description", new BigDecimal("200.00"), null, "new/path/to/file");
        when(adRepository.findById(anyInt())).thenReturn(Optional.of(advertisement));

        advertisementService.updateAdvertisement(updateDTO, 1);

        verify(advertisementMapper, times(1)).updateAdvertisement(updateDTO, advertisement);
        verify(adRepository).findById(anyInt());
    }

    @Test
    void updateAdvertisement_ShouldThrowException_WhenAdvertisementNotFound() {
        when(adRepository.findById(anyInt())).thenReturn(Optional.empty());

        AdvertisementUpdateDTO updateDTO = new AdvertisementUpdateDTO("Electronics", "Updated Description", new BigDecimal("200.00"), null, "new/path/to/file");

        assertThrows(RuntimeException.class, () -> advertisementService.updateAdvertisement(updateDTO, 1));
        verify(adRepository, times(1)).findById(anyInt());
        verify(advertisementMapper, never()).updateAdvertisement(any(AdvertisementUpdateDTO.class), any(Advertisement.class));
    }

    @Test
    void deleteAdvertisementById_ShouldDeleteAdvertisement() {
        doNothing().when(adRepository).deleteById(anyInt());

        advertisementService.deleteAdvertisementById(1);

        verify(adRepository).deleteById(anyInt());
    }
}
