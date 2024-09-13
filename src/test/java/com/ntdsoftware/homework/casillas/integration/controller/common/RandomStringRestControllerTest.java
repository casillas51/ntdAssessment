package com.ntdsoftware.homework.casillas.integration.controller.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the RandomStringRestController class.
 * Tests the endpoints for generating random strings.
 */
public class RandomStringRestControllerTest extends CommonControllerConfig {

    /** User URL */
    @Value("${application.api.version1.user}")
    private String URL;

    /**
     * Test for the random string operation.
     * @throws Exception - Exception
     */
    @Test
    void whenGenerateString_thenReturns200() throws Exception {
        mockMvc.perform(post(URL + "/operation/random-string")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
