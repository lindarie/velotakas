package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class ForumPost {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 200)
    private String section;

    @Column(nullable = false, length = 30)
    private String topic;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(updatable = false, nullable = false)
    private Time createdAt;

    @Column(insertable = false)
    private Time updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private Forum forum;

    @OneToMany(mappedBy = "forumPost")
    private Set<ForumComment> forumComments;
}
