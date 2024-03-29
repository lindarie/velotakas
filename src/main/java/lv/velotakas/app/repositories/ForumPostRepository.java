package lv.velotakas.app.repositories;

import lv.velotakas.app.models.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Integer> {
}
