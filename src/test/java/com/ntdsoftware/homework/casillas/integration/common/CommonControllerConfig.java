package com.ntdsoftware.homework.casillas.integration.common;

import com.jayway.jsonpath.JsonPath;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Common controller configuration.
 */
@AutoConfigureMockMvc
public class CommonControllerConfig implements ApplicationTest {

    /** Login URL */
    @Value("${application.api.version1.auth}")
    private String LoginURL;

    /** Mock MVC */
    @Autowired
    protected MockMvc mockMvc;

    /** Token */
    protected String token;

    /**
     * Login
     * @throws Exception - Exception
     */
    @BeforeEach
    void login() throws Exception {
        MvcResult result = mockMvc.perform(post(LoginURL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"User1\",\"password\":\"User123\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        this.token = JsonPath.parse(response).read("$.token");
    }
}
