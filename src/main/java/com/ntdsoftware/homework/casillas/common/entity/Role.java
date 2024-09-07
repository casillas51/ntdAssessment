package com.ntdsoftware.homework.casillas.common.entity;

import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Role represents a role entity in the system.
 * It includes fields for the role ID and the role name, which is an enum of type RolesEnum.
 */
@Table(name = "roles")
@Entity
@Data
@Accessors(chain = true)
public class Role {

    /** The unique identifier for the role. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
    @SequenceGenerator(name = "role_id_generator", sequenceName = "roles_seq", allocationSize = 1)
    @Column(name = "id_role", nullable = false)
    private int id;

    /** The name of the role, which is an enum of type RolesEnum. */
    @Column(name = "role", nullable = false, unique = true, columnDefinition = "ENUM('ADMIN', 'USER')")
    @Enumerated(EnumType.STRING)
    private RolesEnum role;

}
