package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicycleRepository extends JpaRepository<Bicycle, Integer> {
}
