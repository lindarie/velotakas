package lv.velotakas.app.service.impl;

import lombok.RequiredArgsConstructor;
import lv.velotakas.app.dto.request.service.ServiceRequest;
import lv.velotakas.app.dto.request.service.ServiceUpdateRequest;
import lv.velotakas.app.dto.response.ServiceDTO;
import lv.velotakas.app.mapper.BikeServiceMapper;
import lv.velotakas.app.mapper.UserMapper;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.ServiceRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.BikeServiceService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BikeServiceServiceImpl implements BikeServiceService {

    private final ServiceRepository repository;
    private final UserRepository userRepository;
    private final BikeServiceMapper mapper = Mappers.getMapper( BikeServiceMapper.class );
    private final UserMapper userMapper = Mappers.getMapper( UserMapper.class );

    @Override
    @Transactional(readOnly = true)
    public boolean serviceExistsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteServiceById(Integer id) {
            repository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateBikeService(ServiceUpdateRequest updateRequest, Integer id) {
        lv.velotakas.app.models.Service bikeService = repository.findById(id).orElseThrow(() -> new RuntimeException("Bike service not found"));
        mapper.updateService(updateRequest, bikeService);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceDTO findServiceById(Integer id) {
        lv.velotakas.app.models.Service bikeService = repository.findById(id).orElseThrow(() -> new RuntimeException("Bike service not found"));
        return mapper.toServiceDTO(bikeService);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceDTO> findAllBikeServices() {
        return repository.findAll().stream()
                .map(mapper::toServiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ServiceDTO createBikeService(ServiceRequest request) {
        lv.velotakas.app.models.Service bikeService = mapper.toBikeService(request);
        User user = userRepository.findByEmail(request.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        bikeService.setUser(user);
        repository.save(bikeService);
        ServiceDTO dto = mapper.toServiceDTO(bikeService);
        dto.setUser(userMapper.userToUserDAO(user));
        return dto;
    }
}
