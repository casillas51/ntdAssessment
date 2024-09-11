package com.ntdsoftware.homework.casillas.integration.controller.common;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for the subtraction controller.
 */
public class SubtractionRestControllerTest extends CommonControllerConfig {

    /** User URL */
    @Value("${application.api.version1.user}")
    private String URL;

    /**
     * Test for the subtraction operation.
     * @throws Exception - Exception
     */
    @Test
    void whenSubtraction_thenReturns200() throws Exception {
        mockMvc.perform(post(URL + "/operation/subtraction")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"minuend\":\"10\",\"subtrahend\":\"5\"}"))
                .andExpect(status().isOk());
    }
}
