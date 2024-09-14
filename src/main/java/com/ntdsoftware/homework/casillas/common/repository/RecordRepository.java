package com.ntdsoftware.homework.casillas.common.repository;

import com.ntdsoftware.homework.casillas.common.entity.Record;
import com.ntdsoftware.homework.casillas.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repository interface for managing records.
 * Provides methods for performing CRUD operations on records.
 */
public interface RecordRepository extends JpaRepository<Record, Integer>, JpaSpecificationExecutor<Record> {

    /**
     * Finds a record by its ID and user.
     *
     * @param recordId the ID of the record
     * @param user the user associated with the record
     * @return an Optional containing the found record, or empty if no record was found
     */
    Optional<Record> findByIdAndUser(int recordId, User user);

}