package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;

@Data
@NoArgsConstructor
@Entity
public class ForumComment {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 300)
    private String message;

    @Column(updatable = false, nullable = false)
    private Time createdAt;

    @Column(insertable = false)
    private Time updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private ForumPost forumPost;
}
