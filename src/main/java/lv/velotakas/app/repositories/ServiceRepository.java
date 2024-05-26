package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Modifying
    @Query("DELETE FROM Service s WHERE s.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Integer userId);
}
