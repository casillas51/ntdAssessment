package com.ntdsoftware.homework.casillas.common.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The request object for the square root operation.
 */
@Data
@Accessors(chain = true)
public class SquareRootRequest {

    /**
     * The radicand value.
     */
    @NotNull(message = "Radicand value is required")
    @Min(value = 0, message = "Radicand cannot be negative")
    private Double radicand;
}
