/*
 * Copyright (c)  IInfo 2021.
 */

package com.i1nfo.icb.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.i1nfo.icb.exception.UnauthorizedException;
import com.i1nfo.icb.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationException(@NotNull MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        HashMap<String, Object> hashMap = new HashMap<>();
        for (FieldError error :
                fieldErrors) {
            hashMap.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(ErrorResponse.builder().msg("bad request").error(hashMap).build());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequestException() {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse
                        .builder()
                        .msg("bad request")
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleJWTCreateException(@NotNull JWTCreationException exception) {
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse
                        .builder()
                        .msg("JWT Creation error")
                        .error(exception.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(@NotNull JWTVerificationException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse
                        .builder()
                        .msg("JWT verification error")
                        .error(exception.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(@NotNull UnauthorizedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse
                        .builder()
                        .msg("unauthorized")
                        .error(exception.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAnyException(@NotNull Exception exception) {
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse
                        .builder()
                        .msg("unknown exception")
                        .error(exception.getMessage())
                        .build());
    }

}
