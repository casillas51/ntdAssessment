package com.ntdsoftware.homework.casillas.common.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Request object for addition operations.
 * Contains the terms to be added.
 */
@Data
@Accessors(chain = true)
public class AdditionRequest {

    /** The first term of the addition. */
    @NotNull(message = "Term 1 is required")
    private Double term1;

    /** The second term of the addition. */
    @NotNull(message = "Term 2 is required")
    private Double term2;

}