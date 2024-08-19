package com.ntdsoftware.homework.casillas.security.entity;

import com.ntdsoftware.homework.casillas.security.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.security.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(StatusEnum.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(StatusEnum.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(StatusEnum.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return status.equals(StatusEnum.ACTIVE);
    }
}
