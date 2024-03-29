package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
