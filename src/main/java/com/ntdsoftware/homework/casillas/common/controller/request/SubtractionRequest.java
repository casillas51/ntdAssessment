package com.ntdsoftware.homework.casillas.common.controller.request;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Minuend is required")
    private Double minuend;

    /**
     * The subtrahend in the subtraction operation.
     */
    @NotNull(message = "Subtrahend is required")
    private Double subtrahend;
}