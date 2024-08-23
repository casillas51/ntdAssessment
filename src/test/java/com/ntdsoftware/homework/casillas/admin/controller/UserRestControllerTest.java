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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserRestControllerTest implements ApplicationTest {

    @Value("${application.api.version1.auth}")
    private String LoginURL;

    @Value("${application.api.version1.admin}")
    private String URL;

    @Autowired
    private MockMvc mockMvc;

    private String token;

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

    @Test
    void whenCreateValidUser_thenReturns201() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"TestUser\",\"password\":\"User123\",\"role\":\"USER\",\"active\":true}"))
                .andExpect(status().isCreated());
    }

    @Test
    void whenInputIsNull_thenReturns400() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserExists_thenReturn409() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Admin\",\"password\":\"Adm123\",\"role\":\"ADMIN\",\"active\":true}"))
                .andExpect(status().isConflict());
    }

    @Test
    void whenRoleIsInvalid_thenReturns404() throws Exception {

        mockMvc.perform(post(URL + "/user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"TestUser\",\"password\":\"User123\",\"role\":\"INVALID\",\"active\":true}"))
                .andExpect(status().isNotFound());
    }
}
