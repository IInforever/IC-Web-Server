/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.i1nfo.ic.config.JWTConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {

    JWTConfig jwtConfig;

    Algorithm algorithmEC;

    JWTVerifier verifier;

    public JWTUtils(@NotNull JWTConfig jwtConfig) throws IOException {
        this.jwtConfig = jwtConfig;
        ECPublicKey publicKey = (ECPublicKey) PemUtils.readPublicKeyFromFile(jwtConfig.getPublicKeyPath(), "EC");
        ECPrivateKey privateKey = (ECPrivateKey) PemUtils.readPrivateKeyFromFile(jwtConfig.getPrivateKeyPath(), "EC");
        // Use JWS ES256
        algorithmEC = Algorithm.ECDSA256(publicKey, privateKey);
        // Create JWT verifier
        verifier = JWT.require(algorithmEC)
                .withIssuer(jwtConfig.getIssuer())
                .build();
    }


    /**
     * create JWT with subject
     *
     * @param claims only allow String value
     */
    public String createToken(String subject, Map<String, String> claims) throws JWTCreationException {
        return createToken(subject, new Date(System.currentTimeMillis() + jwtConfig.getExpiresTime() * 1000), claims);
    }

    public String createToken(String subject, Date expiresAt, Map<String, String> claims) throws JWTCreationException {
        JWTCreator.Builder builder = JWT.create();
        if (claims != null)
            claims.forEach(builder::withClaim);
        builder.withSubject(subject)
                .withIssuer(jwtConfig.getIssuer())
                .withExpiresAt(expiresAt)
                .withIssuedAt(new Date(System.currentTimeMillis()));
        return builder.sign(algorithmEC);
    }

    /**
     * @param token Raw JWT token
     */
    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }

    /**
     * auto check token expire time and return new token
     *
     * @return refreshed token when needed, or return null
     */
    public String autoUpdateToken(@NotNull DecodedJWT token) {
        return autoUpdateToken(token, new Date(token.getExpiresAt().getTime() - jwtConfig.getUpdateTime() * 1000));
    }

    public String autoUpdateToken(@NotNull DecodedJWT token, @NotNull Date updateAt) {
        if (updateAt.before(new Date(System.currentTimeMillis()))) {
            HashMap<String, String> map = new HashMap<>();
            token.getClaims().forEach((key, value) -> map.put(key, value.asString()));
            return createToken(token.getSubject(), map);
        }
        return null;
    }

}