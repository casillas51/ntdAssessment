package com.ntdsoftware.homework.casillas.integration.admin;

import com.ntdsoftware.homework.casillas.admin.controller.response.BalanceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the BalanceRestController class.
 * This class tests the various endpoints of the BalanceRestController to ensure they function correctly.
 */
public class AdminBalanceRestControllerTest extends AdminControllerConfig {

    /** Admin URL */
    @Value("${application.api.version1.admin}")
    private String URL;

    /**
     * Test updating balance with valid input.
     * Expects a 200 OK status and the correct balance in the response.
     */
    @Test
    void whenUpdateBalanceWithValidInput_thenReturns200() throws Exception {
        int userId = 2;
        double newBalance = 10.0;
        BalanceResponse response = new BalanceResponse().setUserId(userId).setBalance(newBalance);

        mockMvc.perform(put(URL + "/balance/" + userId + "/update")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("balance", String.valueOf(newBalance)))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"userId\":2,\"balance\":10.0}"));
    }

    /**
     * Test updating balance with an invalid user ID.
     * Expects a 404 Not Found status.
     */
    @Test
    void whenUpdateBalanceWithInvalidUserId_thenReturns404() throws Exception {
        int userId = 0;
        double newBalance = 10.0;

        mockMvc.perform(put(URL + "/balance/" + userId + "/update")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("balance", String.valueOf(newBalance)))
                        .andExpect(status().isNotFound());
    }

    /**
     * Test updating balance with a negative balance.
     * Expects a 400 Bad Request status.
     */
    @Test
    void whenUpdateBalanceWithNegativeBalance_thenReturns400() throws Exception {
        int userId = 2;
        double newBalance = -10.0;

        mockMvc.perform(put(URL + "/balance/" + userId + "/update")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("balance", String.valueOf(newBalance)))
                        .andExpect(status().isBadRequest());
    }

    /**
     * Test depositing balance with valid input.
     * Expects a 200 OK status and the correct balance in the response.
     */
    @Test
    void whenDepositBalanceWithValidInput_thenReturns200() throws Exception {
        int userId = 3;
        double depositAmount = 10.0;

        mockMvc.perform(put(URL + "/balance/" + userId + "/deposit")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("deposit", String.valueOf(depositAmount)))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"userId\":3,\"balance\":40.0}"));
    }

    /**
     * Test depositing balance with an invalid user ID.
     * Expects a 404 Not Found status.
     */
    @Test
    void whenDepositBalanceWithInvalidUserId_thenReturns404() throws Exception {
        int userId = 0;
        double depositAmount = 10.0;

        mockMvc.perform(put(URL + "/balance/" + userId + "/deposit")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("deposit", String.valueOf(depositAmount)))
                        .andExpect(status().isNotFound());
    }

    /**
     * Test depositing balance with a negative amount.
     * Expects a 400 Bad Request status.
     */
    @Test
    void whenDepositBalanceWithNegativeAmount_thenReturns400() throws Exception {
        int userId = 3;
        double depositAmount = -10.0;

        mockMvc.perform(put(URL + "/balance/" + userId + "/deposit")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("deposit", String.valueOf(depositAmount)))
                        .andExpect(status().isBadRequest());
    }
}