package lv.velotakas.app.repositories;

import lv.velotakas.app.models.ServiceRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRatingRepository extends JpaRepository<ServiceRating, Integer> {
}
