package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String surname;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false)
    private Boolean twoFactorAuth;

    @OneToMany(mappedBy = "user")
    private Set<ServiceRating> ratings;

    @OneToMany(mappedBy = "user")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "user")
    private Set<Advertisement> advertisements;

    @OneToMany(mappedBy = "user")
    private Set<Service> services;

    @OneToMany(mappedBy = "user")
    private Set<ForumPost> forumPosts;

    @OneToMany(mappedBy = "user")
    private Set<ForumComment> forumComments;

    @OneToMany(mappedBy = "user")
    private Set<Bicycle> bicycles;

    @OneToMany(mappedBy = "user")
    private Set<Complaint> complaints;

    @OneToMany(mappedBy = "user")
    private Set<Trail> trails;

    @OneToMany(mappedBy = "user")
    private Set<TrailRating> trailRatings;

    @OneToMany(mappedBy = "user")
    private Set<MapObject> mapObjects;

    @OneToMany(mappedBy = "user")
    private Set<MapObjectRating> mapObjectRatings;
}
