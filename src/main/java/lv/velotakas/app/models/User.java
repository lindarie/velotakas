package lv.velotakas.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.velotakas.app.models.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "\"User\"")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String surname;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 20)
    private Role role;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
