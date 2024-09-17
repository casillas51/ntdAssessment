package com.ntdsoftware.homework.casillas.integration.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the AdminRecordRestController.
 * This class tests the endpoints for managing admin records.
 */
public class AdminRecordRestControllerTest extends AdminControllerConfig {

    /**
     * The base URL for admin record endpoints.
     */
    @Value("${application.api.version1.admin}")
    private String URL;

    /**
     * Tests that all records are retrieved successfully with a status of 200.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    void whenGetAllRecords_thenReturns200() throws Exception {
        mockMvc.perform(get(URL + "/record")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    /**
     * Tests that records are searched successfully with a status of 200.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    void whenSearchRecords_thenReturns200() throws Exception {
        mockMvc.perform(post(URL + "/record")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .content("{\"query\": " +
                        "       {\"operation\": null, " +
                        "        \"amount\": null, " +
                        "        \"userBalance\": null, " +
                        "        \"response\": null, " +
                        "        \"dateFrom\": null, " +
                        "        \"dateTo\": null, " +
                        "        \"isDeleted\": null" +
                        "       }," +
                        " \"page\": 0," +
                        " \"size\": 10}"))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a record is deleted successfully with a status of 200.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    void whenDeleteRecord_thenReturns200() throws Exception {
        mockMvc.perform(delete(URL + "/record/2")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isAccepted());
    }

}