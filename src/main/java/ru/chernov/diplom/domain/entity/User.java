package ru.chernov.diplom.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.chernov.diplom.domain.PrivilegeLevel;
import ru.chernov.diplom.domain.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Pavel Chernov
 */
@Entity
@Getter
@Table(name = "usr")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Setter
    @Column(length = 25, nullable = false)
    private String username;

    @Setter
    @Column(length = 25, nullable = false)
    private String name;

    @Setter
    @Column(length = 25, nullable = false)
    private String surname;

    @Setter
    @Column(length = 25)
    private String middleName;

    @Setter
    @Column(nullable = false)
    private int passportId;

    @Setter
    @Column(nullable = false)
    private int passportSeries;

    @Column(nullable = false)
    private double spent = 0.0;

    // real length is 25
    // passwordEncoder needs more
    @Setter
    @Column(length = 100, nullable = false)
    @JsonIgnore
    private String password;

    @Setter
    @Column(nullable = false)
    @JsonIgnore
    private boolean isActive;

    @Setter
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, updatable = false)
    private Set<Role> roles = new HashSet<>();

    public User(String name, String surname, String middleName, int passportId, int passportSeries) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.passportId = passportId;
        this.passportSeries = passportSeries;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
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

    public PrivilegeLevel getPrivilegeLevel() {
        return PrivilegeLevel.getLevel(this.spent);
    }

    public void addToSpent(double amount) {
        this.spent += amount;
    }
}
