package com.ntdsoftware.homework.casillas.admin.controller;

import com.jayway.jsonpath.JsonPath;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User Rest Controller test
 */
@AutoConfigureMockMvc
public class UserRestControllerTest implements ApplicationTest {

    /** Login URL */
    @Value("${application.api.version1.auth}")
    private String LoginURL;

    /** User URL */
    @Value("${application.api.version1.admin}")
    private String URL;

    /** Mock MVC */
    @Autowired
    private MockMvc mockMvc;

    /** Token */
    private String token;

    /**
     * Login
     * @throws Exception - Exception
     */
    @BeforeEach
    void login() throws Exception {
        MvcResult result = mockMvc.perform(post(LoginURL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Admin\",\"password\":\"Adm123\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        this.token = JsonPath.parse(response).read("$.token");
    }

    /**
     * Create a new user
     * @throws Exception - Exception
     */
    @Test
    void whenCreateValidUser_thenReturns201() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"TestUser\",\"password\":\"User123\",\"role\":\"USER\",\"active\":true}"))
                .andExpect(status().isCreated());
    }

    /**
     * Create a new user with invalid input
     * @throws Exception - Exception
     */
    @Test
    void whenInputIsNull_thenReturns400() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Create a new user with existing username
     * @throws Exception - Exception
     */
    @Test
    void whenUserExists_thenReturn409() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Admin\",\"password\":\"Adm123\",\"role\":\"ADMIN\",\"active\":true}"))
                .andExpect(status().isConflict());
    }

    /**
     * Create a new user with invalid role
     * @throws Exception - Exception
     */
    @Test
    void whenRoleIsInvalid_thenReturns404() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"TestUser\",\"password\":\"User123\",\"role\":\"INVALID\",\"active\":true}"))
                .andExpect(status().isNotFound());
    }

    /**
     * Get existing user
     * @throws Exception - Exception
     */
    @Test
    void whenGetExistingUser_thenReturnOk() throws Exception {

        mockMvc.perform(get(URL + "/user/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    /**
     * Get non-existing user
     * @throws Exception - Exception
     */
    @Test
    void whenGetNonExistingUser_thenReturn404() throws Exception {

        mockMvc.perform(get(URL + "/user/0")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    /**
     * Update existing user
     * @throws Exception - Exception
     */
    @Test
    void whenUpdateUser_thenReturns200() throws Exception {

        mockMvc.perform(put(URL + "/user/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Admin\",\"password\":\"Adm123\",\"role\":\"ADMIN\",\"active\":true}"))
                .andExpect(status().isOk());
    }

    /**
     * Update non-existing user
     * @throws Exception - Exception
     */
    @Test
    void whenUpdateNonExistingUser_thenReturns404() throws Exception {

        mockMvc.perform(put(URL + "/user/0")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Test\",\"password\":\"User123\",\"role\":\"ADMIN\",\"active\":true}"))
                .andExpect(status().isNotFound());
    }
}
