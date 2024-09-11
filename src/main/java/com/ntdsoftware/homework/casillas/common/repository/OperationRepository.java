package com.ntdsoftware.homework.casillas.common.repository;

import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for the Operation entity.
 * Extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    /**
     * Finds an operation by its ID.
     *
     * @param id the ID of the operation
     * @return an Optional containing the found operation, or empty if not found
     */
    Optional<Operation> findById(int id);

    /**
     * Finds an operation by its type.
     *
     * @param operationType the type of the operation
     * @return an Optional containing the found operation, or empty if not found
     */
    Optional<Operation> findByOperationType(OperationTypeEnum operationType);

}