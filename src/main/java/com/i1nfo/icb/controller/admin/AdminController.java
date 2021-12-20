/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.controller.admin;

import com.i1nfo.icb.config.AppConfig;
import com.i1nfo.icb.utils.JWTUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AppConfig appConfig;

    private final JWTUtils jwtUtils;

    @Autowired
    public AdminController(AppConfig appConfig, JWTUtils jwtUtils) {
        this.appConfig = appConfig;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> adminLogin(@RequestBody @Validated @NotNull Admin info) {
        if (info.getUsername().equals(appConfig.getAdmin().getUsername()) &&
                info.getPassword().equals(appConfig.getAdmin().getPassword())) {
            return ResponseEntity.ok().header("Authorization", jwtUtils.createToken("Admin")).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @Data
    public static class Admin {

        @NotBlank
        private String username;

        @NotBlank
        private String password;

    }

}
