package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Trail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrailRepository extends JpaRepository<Trail, Integer> {
}
