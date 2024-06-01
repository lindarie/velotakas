package lv.velotakas.app.service;

import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.dto.request.trail.TrailObjectDTO;
import lv.velotakas.app.dto.request.trail.UpdateTrailRequest;

import java.util.List;

public interface TrailService {
    TrailDTO createTrail(TrailDTO trailDTO);

    void updateTrail(UpdateTrailRequest updateTrailRequest, Integer id);

    boolean trailExistsById(Integer id);

    TrailDTO getTrailById(Integer id);

    void deleteTrailById(Integer id);

    List<TrailDTO> findAllTrails();

    List<TrailDTO> getTrailsBySurface(String surface);

    TrailObjectDTO addObject(Integer ObjectId, Integer TrailId);
}
