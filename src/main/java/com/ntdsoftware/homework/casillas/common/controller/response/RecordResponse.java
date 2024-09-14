package com.ntdsoftware.homework.casillas.common.controller.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * Response object representing a record of an operation performed by a user.
 * This class is used to transfer data between the client and the server.
 */
@Data
@Accessors(chain = true)
public class RecordResponse {

    /**
     * The unique identifier for the record.
     */
    private int recordId;

    /**
     * The username of the user who performed the operation.
     */
    private String userName;

    /**
     * The type of operation performed.
     */
    private String operation;

    /**
     * The amount involved in the operation.
     */
    private Double amount;

    /**
     * The user's balance after the operation.
     */
    private Double userBalance;

    /**
     * The response of the operation.
     */
    private String response;

    /**
     * The date and time when the record was created.
     */
    private Timestamp date;

    /**
     * Indicates whether the record is deleted.
     */
    private Boolean isDeleted;

}