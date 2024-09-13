package com.ntdsoftware.homework.casillas.common.webClient.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Request object for generating a random string.
 */
@Data
@Accessors(chain = true)
public class GenerateStringRequest {

    /**
     * The JSON-RPC version.
     */
    private String jsonrpc;

    /**
     * The method to be called.
     */
    private String method;

    /**
     * The parameters for the request.
     */
    private ParamsRequest params;

    /**
     * The identifier for the request.
     */
    private int id;
}