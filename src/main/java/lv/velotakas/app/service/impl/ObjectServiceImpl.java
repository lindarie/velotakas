package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.object.ObjectDTO;
import lv.velotakas.app.dto.request.object.UpdateObjectRequest;
import lv.velotakas.app.models.MapObject;
import lv.velotakas.app.models.User;
import lv.velotakas.app.mapper.ObjectMapper;
import lv.velotakas.app.repositories.MapObjectRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.ObjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.println("call1");
        MapObject object = objectMapper.toEntity(objectDTO);
        User user = userRepository.findById(1).orElseThrow(()-> new RuntimeException("User not found"));
        object.setUser(user);
        MapObject savedObject = objectRepository.save(object);
        System.out.println(savedObject.getId());
        return objectMapper.toDTO(savedObject);
    }
    @Override
    @Transactional(readOnly = true)
    public boolean objectExistsById(Integer id) {
        return objectRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ObjectDTO getObjectById(Integer id){

    MapObject object = objectRepository.findById(id).orElseThrow();
    return objectMapper.toDTO(object);


    }

    @Override
    @Transactional
    public void updateObject(UpdateObjectRequest updateRequest, Integer id){
        MapObject object = objectRepository.findById(id).orElseThrow();
        objectMapper.toUpdateRequest(updateRequest, object);
//        objectRepository.save(object);
//        return objectMapper.toDTO(object);
    }

    @Override
    @Transactional
    public void deleteObjectById(Integer id) { objectRepository.deleteById(id); }
}
