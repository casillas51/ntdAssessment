package com.ntdsoftware.homework.casillas.common.webClient.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Response object for generating a random string.
 */
@Data
@Accessors(chain = true)
public class GenerateStringResponse {

    /**
     * The JSON-RPC version.
     */
    private String jsonrpc;

    /**
     * The result of the generate string operation.
     */
    private ResultResponse result;

    /**
     * The identifier for the response.
     */
    private int id;
}