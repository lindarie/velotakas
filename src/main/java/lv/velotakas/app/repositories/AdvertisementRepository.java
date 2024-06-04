package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    List<Advertisement> findByCategory(String category);
    Optional<Advertisement> findById(Integer id);
}
