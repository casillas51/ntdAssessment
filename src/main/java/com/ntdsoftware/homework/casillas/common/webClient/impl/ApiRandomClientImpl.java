package com.ntdsoftware.homework.casillas.common.webClient.impl;

import com.ntdsoftware.homework.casillas.common.exception.RandomApiServiceUnavailable;
import com.ntdsoftware.homework.casillas.common.webClient.IApiRandomClient;
import com.ntdsoftware.homework.casillas.common.webClient.request.GenerateStringRequest;
import com.ntdsoftware.homework.casillas.common.webClient.request.ParamsRequest;
import com.ntdsoftware.homework.casillas.common.webClient.response.GenerateStringResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Api random client implementation.
 */
@Service
@Slf4j
public class ApiRandomClientImpl implements IApiRandomClient {

    /**
     * Rest template.
     */
    private final RestTemplate restTemplate;

    /**
     * Service url.
     */
    @Value("${application.random.url}")
    protected String serviceUrl;

    /**
     * Json_RPC request version.
     */
    @Value("${application.random.json-rpc}")
    protected String json_rpc;

    /**
     * Method to execute.
     */
    @Value("${application.random.method}")
    protected String method;

    /**
     * Api key to log in.
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
     * Characters to included into the result.
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
     * Instantiates a new Api random client.
     *
     * @param restTemplate the rest template
     */
    public ApiRandomClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GenerateStringResponse generateString() {

        log.info("Generating random string");

        HttpEntity<GenerateStringRequest> request = generateRequest();
        GenerateStringResponse response = restTemplate.postForObject(serviceUrl, request, GenerateStringResponse.class);

        if (null == response) {
            log.error("Random API service unavailable");
            throw new RandomApiServiceUnavailable();
        }

        return response;
    }

    /**
     * Generate request http entity.
     *
     * @return the http entity
     */
    private HttpEntity<GenerateStringRequest> generateRequest() {

        final GenerateStringRequest request = new GenerateStringRequest()
                .setJsonrpc(json_rpc)
                .setMethod(method)
                .setParams(new ParamsRequest()
                        .setApiKey(apiKey)
                        .setN(Integer.parseInt(n))
                        .setLength(Integer.parseInt(length))
                        .setCharacters(characters)
                        .setReplacement(Boolean.parseBoolean(replacement)))
                .setId(Integer.parseInt(id));

        final HttpHeaders headers = generateHeaders();

        log.info("Header: {}, Request: {}", headers, request);
        return new HttpEntity<>(request, headers);
    }

    /**
     * Generate headers http headers.
     *
     * @return the http headers
     */
    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
