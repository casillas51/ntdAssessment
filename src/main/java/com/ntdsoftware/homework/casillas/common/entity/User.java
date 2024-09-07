package com.ntdsoftware.homework.casillas.common.entity;

import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * User is an entity representing a user in the system.
 * It implements the UserDetails interface from Spring Security to provide user authentication and authorization details.
 * The entity is mapped to the "users" table in the database and includes fields such as id, username, password, status, createdDate, role, and deleted.
 * It also provides methods to map the user to a response object and to get authorities and account status details.
 */
@Table(name = "users")
@Entity
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id_user=?")
@Data
@Accessors(chain = true)
public class User implements UserDetails {

    /** The id of the user */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_seq", allocationSize = 1)
    @Column(name = "id_user", nullable = false)
    private int id;

    /** The username of the user */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /** The password of the user */
    @Column(name = "password", nullable = false)
    private String password;

    /** The status of the user */
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    /** The created date of the user */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdDate;

    /** The role of the user */
    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    /** The deleted status of the user */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

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

    /**
     * Map the user to a response object.
     *
     * @return the response object
     */
    public UserResponse mapToResponse() {
        return UserResponse.builder()
                .id(id)
                .username(username)
                .active(status)
                .role(role.getRole())
                .createdDate(createdDate)
                .deleted(deleted)
                .build();
    }
}
