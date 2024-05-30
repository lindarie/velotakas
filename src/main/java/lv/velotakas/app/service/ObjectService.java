package lv.velotakas.app.service;
import lv.velotakas.app.dto.request.object.ObjectDTO;
import lv.velotakas.app.dto.request.object.UpdateObjectRequest;

public interface ObjectService {

    ObjectDTO createObject(ObjectDTO objectDTO);

    void deleteObjectById(Integer id);

    void updateObject(UpdateObjectRequest updateObjectRequest, Integer id);

    boolean objectExistsById(Integer id);

    ObjectDTO getObjectById(Integer id);


}
