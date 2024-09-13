package com.ntdsoftware.homework.casillas.common.webClient;

import com.ntdsoftware.homework.casillas.common.webClient.response.GenerateStringResponse;

/**
 * Api random client.
 */
public interface IApiRandomClient {

    /**
     * Generate string.
     *
     * @return the generate string response
     */
    GenerateStringResponse generateString();

}
