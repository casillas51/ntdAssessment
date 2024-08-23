package com.ntdsoftware.homework.casillas.common.entity;

import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "roles")
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
    @SequenceGenerator(name = "role_id_generator", sequenceName = "roles_seq", allocationSize = 1)
    @Column(name = "id_role", nullable = false)
    private int id;

    @Column(name = "role", nullable = false, unique = true, columnDefinition = "ENUM('ADMIN', 'USER')")
    @Enumerated(EnumType.STRING)
    private RolesEnum role;

}
