package com.ntdsoftware.homework.casillas.common.controller.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Request object for subtraction operations.
 */
@Data
@Accessors(chain = true)
public class SubtractionRequest {

    /**
     * The minuend in the subtraction operation.
     */
    private Double minuend;

    /**
     * The subtrahend in the subtraction operation.
     */
    private Double subtrahend;
}