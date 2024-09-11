package com.ntdsoftware.homework.casillas.integration.controller.common;

import com.ntdsoftware.homework.casillas.common.controller.request.MultiplicationRequest;
import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;
import com.ntdsoftware.homework.casillas.common.service.IMultiplicationService;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the MultiplicationRestController class.
 * Tests the endpoints for performing multiplication operations.
 */
public class MultiplicationRestControllerTest extends CommonControllerConfig {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMultiplicationService multiplicationService;

    /**
     * Tests the multiplicate endpoint to ensure it performs the multiplication operation correctly.
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
     * Tests the multiplicate endpoint to ensure it throws an exception when null values are provided.
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