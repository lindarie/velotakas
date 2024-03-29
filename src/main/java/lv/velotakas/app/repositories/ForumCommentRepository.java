package lv.velotakas.app.repositories;

import lv.velotakas.app.models.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumCommentRepository extends JpaRepository<ForumComment, Integer> {
}
