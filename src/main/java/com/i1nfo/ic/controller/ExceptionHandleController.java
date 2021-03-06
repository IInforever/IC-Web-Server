/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.i1nfo.ic.exception.NotAllowedException;
import com.i1nfo.ic.exception.UnauthorizedException;
import com.i1nfo.ic.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
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
                        .code(50)
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
                        .code(20)
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
                        .code(10)
                        .error(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowedException(@NotNull NotAllowedException exception) {
        if (exception.getType() == NotAllowedException.Type.ADMIN)
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(ErrorResponse
                            .builder()
                            .code(40)
                            .error(exception.getMessage())
                            .build());
        else
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(ErrorResponse
                            .builder()
                            .code(41)
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
                            .code(21)
                            .msg("duplicate key")
                            .build());
        else
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponse
                            .builder()
                            .code(22)
                            .msg("invalid")
                            .error(exception.getMessage())
                            .build());
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(@NotNull ResourceAccessException exception) {
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse
                        .builder()
                        .code(51)
                        .msg("recaptcha request error")
                        .error(exception.getMessage())
                        .build());
    }

}
