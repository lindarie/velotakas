package lv.velotakas.app.service.impl;
import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.mapper.TrailMapper;
import lv.velotakas.app.models.Trail;
import lv.velotakas.app.models.User;
import lv.velotakas.app.repositories.TrailRepository;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.TrailService;
import org.springframework.stereotype.Service;



@Service
public class TrailServiceImpl implements TrailService {

    public TrailServiceImpl(TrailRepository trailRepository, TrailMapper trailMapper, UserRepository userRepository){
        this.trailRepository = trailRepository;
        this.trailMapper = trailMapper;
        this.userRepository = userRepository;
    }
    private final TrailRepository trailRepository;
    private final TrailMapper trailMapper;
    private final UserRepository userRepository;

    public TrailDTO createTrail(TrailDTO trailDTO){
        Trail trail = trailMapper.toEntity(trailDTO);
        User user = userRepository.findById(1).orElseThrow(()-> new RuntimeException("User not found"));
        trail.setUser(user);
        Trail savedTrail = trailRepository.save(trail);
        return trailMapper.toDTO(savedTrail);
    }
}
