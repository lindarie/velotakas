package lv.velotakas.app.service.impl;
import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.dto.request.trail.UpdateTrailRequest;
import lv.velotakas.app.mapper.TrailMapper;
import lv.velotakas.app.models.Trail;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.TrailRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.TrailService;
import lv.velotakas.app.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;


@Service
public class TrailServiceImpl implements TrailService {

    public TrailServiceImpl(TrailRepository trailRepository, TrailMapper trailMapper, UserRepository userRepository, UserService userService){
        this.trailRepository = trailRepository;
        this.trailMapper = trailMapper;
        this.userRepository = userRepository;
    }
    private final TrailRepository trailRepository;
    private final TrailMapper trailMapper;
    private final UserRepository userRepository;

    public TrailDTO createTrail(TrailDTO trailDTO){
        Trail trail = trailMapper.toEntity(trailDTO);
        User user = userRepository.findByEmail(trailDTO.getUserEmail()).orElseThrow(()-> new RuntimeException("User not found"));
        trail.setUser(user);
        Trail savedTrail = trailRepository.save(trail);
        return trailMapper.toDTO(savedTrail);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean trailExistsById(Integer id) {
        return trailRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TrailDTO getTrailById(Integer id){
        Trail trail = trailRepository.findById(id).orElseThrow();
        return trailMapper.toDTO(trail);
    }

    @Override
    @Transactional
    public void updateTrail(UpdateTrailRequest updateTrailRequest, Integer id){
        Trail trail = trailRepository.findById(id).orElseThrow();
        trailMapper.toUpdateRequest(updateTrailRequest, trail);
    }

    @Override
    @Transactional
    public void deleteTrailById(Integer id) { trailRepository.deleteById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<TrailDTO> findAllTrails(){
        return trailRepository.findAll().stream().map(trailMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrailDTO> getTrailsBySurface(String surface) {
        return trailRepository.findBySurface(surface).stream().map(trailMapper::toDTO).collect(Collectors.toList());
    }
}
