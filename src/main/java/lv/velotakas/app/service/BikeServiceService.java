package lv.velotakas.app.service;

import lv.velotakas.app.dto.request.service.ServiceRequest;
import lv.velotakas.app.dto.request.service.ServiceUpdateRequest;
import lv.velotakas.app.dto.response.ServiceDTO;

import java.util.List;

public interface BikeServiceService {
    boolean serviceExistsById(Integer id);

    void deleteServiceById(Integer id);

    void updateBikeService(ServiceUpdateRequest updateRequest, Integer id);

    ServiceDTO findServiceById(Integer id);

    List<ServiceDTO> findAllBikeServices();

    ServiceDTO createBikeService(ServiceRequest request);
}
