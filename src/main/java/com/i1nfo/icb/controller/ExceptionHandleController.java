/*
 * Copyright (c) IInfo 2021.
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ErrorResponse> handleJWTCreateException(@NotNull JWTCreationException exception) {
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse
                        .builder()
                        .msg("JWT Creation error")
                        .error(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(@NotNull JWTVerificationException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse
                        .builder()
                        .msg("JWT verification error")
                        .error(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(@NotNull UnauthorizedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse
                        .builder()
                        .msg("unauthorized")
                        .error(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLConstraintViolation(@NotNull SQLIntegrityConstraintViolationException exception) {
        if (exception.getErrorCode() == 1062)
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse
                            .builder()
                            .msg("duplicate entry for key")
                            .build());
        else
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse
                            .builder()
                            .msg("invalid")
                            .error(exception.getMessage())
                            .build());
    }

}
