package com.ntdsoftware.homework.casillas.common.repository;

import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository is a Spring Data JPA repository for managing User entities.
 * It provides methods to perform CRUD operations and custom queries on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Counts the number of users with the specified status and role.
     *
     * @param status the status of the users to count
     * @param role the role of the users to count
     * @return the number of users with the specified status and role
     */
    long countByStatusAndRole_role(StatusEnum status, RolesEnum role);
}