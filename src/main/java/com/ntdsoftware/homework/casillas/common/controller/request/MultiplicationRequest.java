package com.ntdsoftware.homework.casillas.common.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Request object for multiplication operations.
 */
@Data
@Accessors(chain = true)
public class MultiplicationRequest {

    /**
     * The multiplicand in the multiplication operation.
     */
    @NotNull(message = "Multiplicand is required")
    private Double multiplicand;

    /**
     * The multiplier in the multiplication operation.
     */
    @NotNull(message = "Multiplier is required")
    private Double multiplier;
}