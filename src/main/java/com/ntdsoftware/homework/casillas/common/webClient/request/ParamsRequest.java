package com.ntdsoftware.homework.casillas.common.webClient.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Request parameters for generating a random string.
 */
@Data
@Accessors(chain = true)
public class ParamsRequest {

    /**
     * The API key for authentication.
     */
    private String apiKey;

    /**
     * The number of elements in the string.
     */
    private int n;

    /**
     * The length of the result string.
     */
    private int length;

    /**
     * The characters to include in the result string.
     */
    private String characters;

    /**
     * Specifies whether the random strings should be picked with replacement.
     */
    private Boolean replacement;
}