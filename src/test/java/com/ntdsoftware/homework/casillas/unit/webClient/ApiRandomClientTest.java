package com.ntdsoftware.homework.casillas.unit.webClient;

import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.common.webClient.impl.ApiRandomClientImpl;
import com.ntdsoftware.homework.casillas.common.webClient.response.GenerateStringResponse;
import com.ntdsoftware.homework.casillas.common.webClient.response.RandomResponse;
import com.ntdsoftware.homework.casillas.common.webClient.response.ResultResponse;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ApiRandomClientImpl class.
 * Tests the methods for generating random strings using the API.
 */
@Slf4j
public class ApiRandomClientTest implements ApplicationTest {

    /**
     * The RestTemplate mock instance.
     */
    @Mock
    RestTemplate restTemplate;

    /**
     * The ApiRandomClientImpl instance to test.
     */
    @InjectMocks
    ApiRandomClientImpl apiRandomClient;

    /**
     * Service URL.
     */
    @Value("${application.random.url}")
    protected String serviceUrl;

    /**
     * JSON-RPC request version.
     */
    @Value("${application.random.json-rpc}")
    protected String json_rpc;

    /**
     * Method to execute.
     */
    @Value("${application.random.method}")
    protected String method;

    /**
     * API key to log in.
     */
    @Value("${application.random.params.apiKey}")
    protected String apiKey;

    /**
     * Number of elements in the String.
     */
    @Value("${application.random.params.n}")
    protected String n;

    /**
     * Length of the result.
     */
    @Value("${application.random.params.length}")
    protected String length;

    /**
     * Characters to include in the result.
     */
    @Value("${application.random.params.characters}")
    protected String characters;

    /**
     * Specifies whether the random strings should be picked with replacement.
     */
    @Value("${application.random.params.replacement}")
    protected String replacement;

    /**
     * Uses historical true randomness derived from the corresponding identifier.
     */
    @Value("${application.random.id}")
    protected String id;

    /**
     * Sets up the test environment by injecting the required values into the ApiRandomClientImpl instance.
     */
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(apiRandomClient, "serviceUrl", serviceUrl);
        ReflectionTestUtils.setField(apiRandomClient, "json_rpc", json_rpc);
        ReflectionTestUtils.setField(apiRandomClient, "method", method);
        ReflectionTestUtils.setField(apiRandomClient, "apiKey", apiKey);
        ReflectionTestUtils.setField(apiRandomClient, "n", n);
        ReflectionTestUtils.setField(apiRandomClient, "length", length);
        ReflectionTestUtils.setField(apiRandomClient, "characters", characters);
        ReflectionTestUtils.setField(apiRandomClient, "replacement", replacement);
        ReflectionTestUtils.setField(apiRandomClient, "id", id);
    }

    /**
     * Tests the generateString method to ensure it successfully generates a random string.
     */
    @Test
    void whenGenerateString_thenSuccess() {
        RandomResponse randomResponse = new RandomResponse().setData(new String[]{"gomEwFeMkmuYWGlvSDoywdisYRYfdPEE"}).setCompletionTime("2024-09-12 23:49:34Z");
        ResultResponse resultResponse = new ResultResponse().setRandom(randomResponse);
        GenerateStringResponse response = new GenerateStringResponse().setId(7).setJsonrpc("2.0").setResult(resultResponse);

        when(restTemplate.postForObject(anyString(), any(Object.class), any())).thenReturn(response);

        GenerateStringResponse generateStringResponse = apiRandomClient.generateString();

        assertNotNull(generateStringResponse);
        log.info("generateStringResponse: {}", generateStringResponse);
    }

    /**
     * Tests the generateString method to ensure it throws an exception when the response is null.
     */
    @Test
    void whenGenerateStringReturnNullResponse_thenThrowException() {
        when(restTemplate.postForObject(anyString(), any(Object.class), any())).thenReturn(null);
        assertThrows(ApplicationException.class, () -> apiRandomClient.generateString());
    }

}