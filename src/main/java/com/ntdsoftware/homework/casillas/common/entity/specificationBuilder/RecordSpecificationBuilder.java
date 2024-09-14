package com.ntdsoftware.homework.casillas.common.entity.specificationBuilder;

import com.ntdsoftware.homework.casillas.common.entity.Operation;
import com.ntdsoftware.homework.casillas.common.entity.Record;
import com.ntdsoftware.homework.casillas.common.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * Builder class for creating specifications for querying records.
 * This class is used to encapsulate the parameters for building record specifications.
 */
@Data
@Accessors(chain = true)
public class RecordSpecificationBuilder implements SpecificationBuilder<Record> {

    /**
     * The user who performed the operation.
     */
    private User user;

    /**
     * The operation associated with the record.
     */
    private Operation operation;

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

    /**
     * Builds the specification map with the provided parameters.
     * This method populates the specification map with the values of the fields.
     */
    @Override
    public void build() {
        specificationMap.put("user", getUser());
        specificationMap.put("operation", getOperation());
        specificationMap.put("amount", getAmount());
        specificationMap.put("userBalance", getUserBalance());
        specificationMap.put("response", getResponse());
        specificationMap.put("dateFrom", getDateFrom());
        specificationMap.put("dateTo", getDateTo());
        specificationMap.put("deleted", getIsDeleted());
    }

}