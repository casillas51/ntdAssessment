package com.ntdsoftware.homework.casillas.common.entity.specificationBuilder;

import com.ntdsoftware.homework.casillas.common.entity.Role;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * UserSpecificationBuilder is responsible for building specifications for querying User entities.
 * It implements the SpecificationBuilder interface and provides the necessary fields and methods
 * to construct the specification map and build the specification.
 */
@Data
@Accessors(chain = true)
public class UserSpecificationBuilder implements SpecificationBuilder<User> {

    /** The username of the user. */
    private String username;

    /** The status of the user. */
    private StatusEnum status;

    /** The start date for filtering users created from this date. */
    private Timestamp createdDateFrom;

    /** The end date for filtering users created up to this date. */
    private Timestamp createdDateTo;

    /** The role of the user. */
    private Role role;

    /** Indicates whether the user is deleted. */
    private Boolean deleted;

    @Override
    public void build() {
        specificationMap.put("username", getUsername());
        specificationMap.put("status", getStatus());
        specificationMap.put("createdDateFrom", getCreatedDateFrom());
        specificationMap.put("createdDateTo", getCreatedDateTo());
        specificationMap.put("role", getRole());
        specificationMap.put("deleted", getDeleted());
    }
}
