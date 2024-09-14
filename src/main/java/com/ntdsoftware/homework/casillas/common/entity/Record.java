package com.ntdsoftware.homework.casillas.common.entity;

import com.ntdsoftware.homework.casillas.common.controller.response.RecordResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * Entity representing a record of an operation performed by a user.
 * This class maps to the "records" table in the database.
 */
@Table(name = "records")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Record {

    /**
     * The unique identifier for the record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_id_generator")
    @SequenceGenerator(name = "record_id_generator", sequenceName = "records_seq", allocationSize = 1)
    @Column(name = "id_record", nullable = false)
    private int id;

    /**
     * The operation associated with the record.
     */
    @ManyToOne
    @JoinColumn(name = "id_operation", nullable = false)
    private Operation operation;

    /**
     * The user who performed the operation.
     */
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    /**
     * The amount involved in the operation.
     */
    @Column(name = "amount", nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private Double amount;

    /**
     * The user's balance after the operation.
     */
    @Column(name = "user_balance", nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private Double userBalance;

    /**
     * The response of the operation.
     */
    @Column(name = "operation_response")
    private String operationResponse;

    /**
     * The date and time when the record was created.
     */
    @Column(name = "date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

    /**
     * Indicates whether the record is deleted.
     */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    /**
     * Maps the record to a RecordResponse object.
     *
     * @return the RecordResponse object
     */
    public RecordResponse mapToResponse() {
        return new RecordResponse()
                .setRecordId(id)
                .setUserName(user.getUsername())
                .setOperation(operation.getOperationType().name())
                .setAmount(amount)
                .setUserBalance(userBalance)
                .setResponse(operationResponse)
                .setDate(date)
                .setIsDeleted(deleted);
    }

}