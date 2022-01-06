/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i1nfo.icb.component.RecaptchaRequests;
import com.i1nfo.icb.config.AppConfig;
import com.i1nfo.icb.model.RecaptchaResponse;
import com.i1nfo.icb.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RecaptchaInterceptor implements HandlerInterceptor {

    private final
    ObjectMapper objectMapper;

    private final
    RecaptchaRequests recaptchaRequests;

    private final
    AppConfig config;

    public RecaptchaInterceptor(ObjectMapper objectMapper, RecaptchaRequests recaptchaRequests, AppConfig config) {
        this.objectMapper = objectMapper;
        this.recaptchaRequests = recaptchaRequests;
        this.config = config;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        String recaptchaToken = request.getHeader("Recaptcha-Token");
        // if no token provided
        if (recaptchaToken == null || recaptchaToken.isBlank()) {
            returnBadRequestResponse(response, 23, "no recaptcha token", null);
            return false;
        }

        // validate token
        ResponseEntity<RecaptchaResponse> responseEntity = recaptchaRequests.verify(recaptchaToken);

        RecaptchaResponse body = responseEntity.getBody();
        if (body == null) {
            returnBadGatewayResponse(response);
            return false;
        }

        if (body.isSuccess()) {
            // check hostname
            if (body.getHostname().equals(config.getHostname()))
                return true;

            returnBadRequestResponse(response, 24, "invalid hostname", null);
            return false;
        }

        returnBadRequestResponse(response, 25, "recaptcha validation fail", body.getErrorCodes());

        return false;
    }

    private void returnBadGatewayResponse(@NotNull HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_GATEWAY.value());
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse
                .builder()
                .code(52)
                .msg("API Request get bad response")
                .build()));
    }

    private void returnBadRequestResponse(@NotNull HttpServletResponse response, int code, String msg, Object error) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse
                .builder()
                .code(code)
                .msg(msg)
                .error(error)
                .build()));
    }

}
