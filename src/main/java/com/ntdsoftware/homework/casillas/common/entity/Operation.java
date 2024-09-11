package com.ntdsoftware.homework.casillas.common.entity;

import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Entity class representing an operation in the system.
 * This class maps to the "operations" table in the database.
 */
@Table(name = "operations")
@Entity
@Data
@Accessors(chain = true)
public class Operation {

    /** The unique identifier for the operation. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_id_generator")
    @SequenceGenerator(name = "operation_id_generator", sequenceName = "operations_seq", allocationSize = 1)
    @Column(name = "id_operation", nullable = false)
    private int id;

    /** The type of the operation, which is an enum of type OperationTypeEnum. */
    @Column(name = "type", nullable = false, unique = true,
            columnDefinition = "ENUM('ADDITION', 'SUBTRACTION', 'MULTIPLICATION', " +
                    "'DIVISION', 'SQUARE_ROOT', 'RANDOM_STRING')")
    @Enumerated(EnumType.STRING)
    private OperationTypeEnum operationType;

    /** The cost of the operation. */
    @Column(name = "cost", nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private Double cost;
}