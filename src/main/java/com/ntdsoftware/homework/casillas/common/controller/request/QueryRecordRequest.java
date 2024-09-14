package com.ntdsoftware.homework.casillas.common.controller.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * Request object for querying records.
 * This class is used to encapsulate the parameters for querying records.
 */
@Data
@Accessors(chain = true)
public class QueryRecordRequest {

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
     * The start date and time for the query.
     */
    private Timestamp dateFrom;

    /**
     * The end date and time for the query.
     */
    private Timestamp dateTo;

    /**
     * Indicates whether the record is deleted.
     */
    private Boolean isDeleted;
}