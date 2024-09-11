package com.ntdsoftware.homework.casillas.integration.controller.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for the addition controller.
 */
public class AdditionRestControllerTest extends CommonControllerConfig {

    /** User URL */
    @Value("${application.api.version1.user}")
    private String URL;

    /**
     * Test for the addition operation.
     * @throws Exception - Exception
     */
    @Test
    void whenAddition_thenReturns200() throws Exception {
        mockMvc.perform(post(URL + "/operation/addition")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"term1\":\"10\",\"term2\":\"5\"}"))
                .andExpect(status().isOk());
    }

    /**
     * Test for the addition operation with null values.
     * @throws Exception - Exception
     */
    @Test
    void whenAdditionWithNullValues_thenReturns400() throws Exception {
        mockMvc.perform(post(URL + "/operation/addition")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"term1\":null,\"term2\":null}"))
                .andExpect(status().isBadRequest());
    }
}
