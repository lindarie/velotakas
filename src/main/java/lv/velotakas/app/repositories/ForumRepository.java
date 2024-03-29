package lv.velotakas.app.repositories;

import lv.velotakas.app.models.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Integer> {
}
