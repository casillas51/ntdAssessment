package com.ntdsoftware.homework.casillas.common.controller.response;

import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Response object for operation results.
 * Contains the type of operation, the result, and the remaining balance.
 */
@Data
@Accessors(chain = true)
public class OperationResultResponse {

    /** The type of the operation performed. */
    private OperationTypeEnum operationType;

    /** The result of the operation. */
    private String result;

    /** The remaining balance after the operation. */
    private Double balance;

    /** The cost of the operation. */
    private Double cost;

    /**
     * Sets the result of the operation as a numeric value.
     *
     * @param numericResult the numeric result of the operation
     */
    public OperationResultResponse setDoubleResult(Double numericResult) {
        this.result = String.valueOf(numericResult);
        return this;
    }

}