/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.component;

import com.i1nfo.icb.config.AppConfig;
import com.i1nfo.icb.model.RecaptchaResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class RecaptchaRequests {

    private final AppConfig config;

    private final RestTemplate restTemplate;

    private final String URL;

    @Autowired
    public RecaptchaRequests(@NotNull AppConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build();
        this.URL = "https://" +
                config.getRecaptcha().getHostname() +
                "/recaptcha/api/siteverify";
    }

    public ResponseEntity<RecaptchaResponse> verify(String recaptchaToken) {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("secret", config.getRecaptcha().getSecret());
        formData.add("response", recaptchaToken);

        //remote ip parameter
        //map.add("remoteip","10.0.0.0");

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);

        return restTemplate.postForEntity(URL, httpEntity, RecaptchaResponse.class);
    }

}
