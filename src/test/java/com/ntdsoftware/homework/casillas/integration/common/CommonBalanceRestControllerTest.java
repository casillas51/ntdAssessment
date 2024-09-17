package com.ntdsoftware.homework.casillas.integration.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the UserBalanceRestController class.
 * This class tests the deposit endpoint of the UserBalanceRestController to ensure it functions correctly.
 */
public class CommonBalanceRestControllerTest extends CommonControllerConfig {

    /** User URL */
    @Value("${application.api.version1.user}")
    private String URL;

    /**
     * Test getting the balance.
     * Expects a 200 OK status.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenGetBalance_thenReturns200() throws Exception {

        mockMvc.perform(get(URL + "/balance")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test depositing a valid amount.
     * Expects a 200 OK status.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenDepositWithValidDepositAmount_thenReturns200() throws Exception {
        double depositAmount = 10.0;

        mockMvc.perform(put(URL + "/balance")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("deposit", String.valueOf(depositAmount)))
                .andExpect(status().isOk());
    }

    /**
     * Test depositing an invalid amount.
     * Expects a 400 Bad Request status.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenDepositWithInvalidDepositAmount_thenReturns400() throws Exception {
        double depositAmount = -10.0;

        mockMvc.perform(put(URL + "/balance")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("deposit", String.valueOf(depositAmount)))
                .andExpect(status().isBadRequest());
    }

}