/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Data
@Validated
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

    @NotBlank
    private String publicKeyPath;

    @NotBlank
    private String privateKeyPath;

    @Positive
    private Long expiresTime;

    @Positive
    private Long updateTime;

    @NotBlank
    private String issuer;

}
