package lv.velotakas.app.repositories;

import lv.velotakas.app.models.TrailObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrailObjectRepository extends JpaRepository<TrailObject, Integer> {
}
