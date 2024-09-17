package com.ntdsoftware.homework.casillas.integration.common;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the MultiplicationRestController class.
 * Tests the endpoints for performing multiplication operations.
 */
public class MultiplicationRestControllerTest extends CommonControllerConfig {

    /**
     * Tests the multiply endpoint to ensure it performs the multiplication operation correctly.
     */
    @Test
    void whenMultiply_thenPerformMultiplication() throws Exception {

        mockMvc.perform(post("/api/v1/user/operation/multiplication")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"multiplicand\": 10.0, \"multiplier\": 5.0}"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the multiply endpoint to ensure it throws an exception when null values are provided.
     */
    @Test
    void whenMultiplyWithNullValues_thenThrowException() throws Exception {

        mockMvc.perform(post("/api/v1/user/operation/multiplication")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"multiplicand\": null, \"multiplier\": null}"))
                .andExpect(status().isBadRequest());
    }
}