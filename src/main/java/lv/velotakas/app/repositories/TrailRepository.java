package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Trail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrailRepository extends JpaRepository<Trail, Integer> {
    List<Trail> findBySurface(String surface);
}
