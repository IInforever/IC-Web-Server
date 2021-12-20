/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "icb")
public class AppConfig {

    @NotNull
    private Admin admin;

    @Data
    public static class Admin {

        private String username;

        private String password;

    }
}
