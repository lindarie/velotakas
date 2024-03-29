package lv.velotakas.app.repositories;

import lv.velotakas.app.models.MapObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapObjectRepository extends JpaRepository<MapObject, Integer> {
}
