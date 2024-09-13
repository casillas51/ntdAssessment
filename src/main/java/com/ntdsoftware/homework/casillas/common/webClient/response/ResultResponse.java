package com.ntdsoftware.homework.casillas.common.webClient.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Result response.
 */
@Data
@Accessors(chain = true)
public class ResultResponse {

    /**
     * Random response.
     */
    private RandomResponse random;

    /**
     * Bits used.
     */
    private int bitsUsed;

    /**
     * Bits left.
     */
    private int bitsLeft;

    /**
     * Requests left.
     */
    private int requestsLeft;

    /**
     * Advisory delay.
     */
    private int advisoryDelay;

}
