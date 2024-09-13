package com.ntdsoftware.homework.casillas.common.webClient.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Random response.
 */
@Data
@Accessors(chain = true)
public class RandomResponse {

    /**
     * Data.
     */
    private String[] data;

    /**
     * Completion time.
     */
    private String completionTime;
}
