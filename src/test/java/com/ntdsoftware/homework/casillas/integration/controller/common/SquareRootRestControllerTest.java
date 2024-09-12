package com.ntdsoftware.homework.casillas.integration.controller.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the SquareRootRestController class.
 * Tests the endpoints for performing square root operations.
 */
public class SquareRootRestControllerTest extends CommonControllerConfig {

    /** User URL */
    @Value("${application.api.version1.user}")
    private String URL;

    /**
     * Test for the square root operation.
     * @throws Exception - Exception
     */
    @Test
    void whenSquareRoot_thenReturns200() throws Exception {
        mockMvc.perform(post(URL + "/operation/square-root")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"radicand\":25}"))
                .andExpect(status().isOk());
    }

    /**
     * Test for the square root operation with null values.
     * @throws Exception - Exception
     */
    @Test
    void whenSquareRootWithNullValues_thenReturns400() throws Exception {
        mockMvc.perform(post(URL + "/operation/square-root")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"radicand\":null}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test for the square root operation with negative values.
     * @throws Exception - Exception
     */
    @Test
    void whenSquareRootWithNegativeValues_thenReturns400() throws Exception {
        mockMvc.perform(post(URL + "/operation/square-root")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"radicand\":-25}"))
                .andExpect(status().isBadRequest());
    }

}