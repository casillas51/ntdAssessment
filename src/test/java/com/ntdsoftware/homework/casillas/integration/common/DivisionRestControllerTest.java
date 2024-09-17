package com.ntdsoftware.homework.casillas.integration.common;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the DivisionRestController class.
 * Tests the endpoints for performing division operations.
 */
public class DivisionRestControllerTest extends CommonControllerConfig {

    /**
     * Tests the divide endpoint to ensure it performs the division operation correctly.
     */
    @Test
    void whenDivide_thenPerformDivision() throws Exception {

        mockMvc.perform(post("/api/v1/user/operation/division")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dividend\": 10.0, \"divisor\": 5.0}"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the divide endpoint to ensure it throws an exception when null values are provided.
     */
    @Test
    void whenDivideWithNullValues_thenThrowException() throws Exception {

        mockMvc.perform(post("/api/v1/user/operation/division")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dividend\": null, \"divisor\": null}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests the divide endpoint to ensure it throws an exception when dividing by zero.
     */
    @Test
    void whenDivideByZero_thenThrowException() throws Exception {

        mockMvc.perform(post("/api/v1/user/operation/division")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dividend\": 10.0, \"divisor\": 0.0}"))
                .andExpect(status().isBadRequest());
    }

}
