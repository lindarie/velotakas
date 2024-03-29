package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Forum {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "forum")
    private Set<ForumPost> forumPosts;
}
