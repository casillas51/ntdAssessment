package com.ntdsoftware.homework.casillas.common.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The request object for the division operation.
 */
@Data
@Accessors(chain = true)
public class DivisionRequest {

    /**
     * The dividend value.
     */
    @NotNull(message = "Dividend value is required")
    private Double dividend;

    /**
     * The divisor value.
     */
    @NotNull(message = "Divisor value is required")
    private Double divisor;

}
