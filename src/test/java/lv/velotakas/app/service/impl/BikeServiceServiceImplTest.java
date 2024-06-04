package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.service.ServiceRequest;
import lv.velotakas.app.dto.request.service.ServiceUpdateRequest;
import lv.velotakas.app.dto.response.ServiceDTO;
import lv.velotakas.app.mapper.BikeServiceMapper;
import lv.velotakas.app.mapper.UserMapper;
import lv.velotakas.app.models.Service;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.ServiceRepository;
import lv.velotakas.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BikeServiceServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BikeServiceMapper bikeServiceMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private BikeServiceServiceImpl bikeServiceService;

    private ServiceRequest serviceRequest;
    private ServiceUpdateRequest serviceUpdateRequest;
    private Service bikeService;
    private User user;
    private ServiceDTO serviceDTO;

    @BeforeEach
    void setUp() {
        serviceRequest = new ServiceRequest("Service Name", "Comment", "path/to/file", "user@example.com");
        serviceUpdateRequest = new ServiceUpdateRequest("Updated Service Name", "Updated Comment", "new/path/to/file");
        bikeService = new Service();
        user = new User();
        serviceDTO = new ServiceDTO();
    }

    @Test
    void serviceExistsById_ShouldReturnTrue_WhenServiceExists() {
        when(serviceRepository.existsById(anyInt())).thenReturn(true);

        boolean exists = bikeServiceService.serviceExistsById(1);

        assertThat(exists).isTrue();
        verify(serviceRepository).existsById(anyInt());
    }

    @Test
    void serviceExistsById_ShouldReturnFalse_WhenServiceDoesNotExist() {
        when(serviceRepository.existsById(anyInt())).thenReturn(false);

        boolean exists = bikeServiceService.serviceExistsById(1);

        assertThat(exists).isFalse();
        verify(serviceRepository).existsById(anyInt());
    }

    @Test
    void deleteServiceById_ShouldDeleteService() {
        doNothing().when(serviceRepository).deleteById(anyInt());

        bikeServiceService.deleteServiceById(1);

        verify(serviceRepository).deleteById(anyInt());
    }

    @Test
    void updateBikeService_ShouldThrowException_WhenServiceNotFound() {
        when(serviceRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bikeServiceService.updateBikeService(serviceUpdateRequest, 1));
        verify(serviceRepository).findById(anyInt());
        verify(bikeServiceMapper, never()).updateService(any(ServiceUpdateRequest.class), any(Service.class));
    }

    @Test
    void findServiceById_ShouldThrowException_WhenServiceNotFound() {
        when(serviceRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bikeServiceService.findServiceById(1));
        verify(serviceRepository).findById(anyInt());
        verify(bikeServiceMapper, never()).toServiceDTO(any(Service.class));
    }

    @Test
    void createBikeService_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bikeServiceService.createBikeService(serviceRequest));
        verify(serviceRepository, never()).save(any(Service.class));
        verify(bikeServiceMapper, never()).toBikeService(any(ServiceRequest.class));
    }
}
