package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.object.ObjectDTO;
import lv.velotakas.app.models.MapObject;
import lv.velotakas.app.models.User;
import lv.velotakas.app.mapper.ObjectMapper;
import lv.velotakas.app.repositories.MapObjectRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.ObjectService;
import org.springframework.stereotype.Service;

@Service

public class ObjectServiceImpl implements ObjectService {
    public ObjectServiceImpl(MapObjectRepository objectRepository, ObjectMapper objectMapper, UserRepository userRepository) {
        this.objectRepository = objectRepository;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }
    private final MapObjectRepository objectRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public ObjectDTO createObject(ObjectDTO objectDTO) {
        MapObject object = objectMapper.toEntity(objectDTO);
        User user = userRepository.findById(1).orElseThrow(()-> new RuntimeException("User not found"));
        object.setUser(user);
        MapObject savedObject = objectRepository.save(object);
        return objectMapper.toDTO(savedObject);
    }
}
