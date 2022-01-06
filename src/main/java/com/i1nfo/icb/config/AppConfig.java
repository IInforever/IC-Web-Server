/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "icb")
public class AppConfig {

    @NotNull
    private Admin admin;

    @NotNull
    private Recaptcha recaptcha;

    @NotBlank
    private String hostname;

    @Data
    public static class Admin {

        @NotBlank
        private String username;

        @NotBlank
        private String password;

    }

    @Data
    public static class Recaptcha {

        @NotBlank
        private String secret;

        @NotBlank
        private String hostname;

    }

}
