/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.i1nfo.icb.service.PasteService;
import com.i1nfo.icb.service.UserService;
import com.i1nfo.icb.utils.JWTUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import java.util.Date;

@SpringBootTest
@ActiveProfiles("dev")
class IcbApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    PasteService pasteService;

    @Autowired
    JWTUtils jwtUtils;

    @Test
    public void testJWTCreate() {
        System.out.println("----- JWT create test ------");
        System.out.println(jwtUtils.createToken("Test JWT", null));
    }

    @Test
    @AfterTestMethod("testJWTCreate")
    public void testJWTVerify() {
        System.out.println("----- JWT verify test ------");
        String jwt = jwtUtils.createToken("Test JWT", new Date(1700000000000L), null);
        DecodedJWT subject = jwtUtils.verifyToken(jwt);
        Assertions.assertEquals(subject.getSubject(), "Test JWT");
        System.out.println("verified ok");
    }

    @Test
    @AfterTestMethod("testJWTVerify")
    public void testJWTUpdate() {
        System.out.println("----- JWT update test ------");
        String jwt = jwtUtils.createToken("Test JWT", null);
        DecodedJWT subject = jwtUtils.verifyToken(jwt);
        String newJwt = jwtUtils.autoUpdateToken(subject, new Date(0));
        System.out.println(newJwt);
        Assertions.assertNotEquals(jwt, newJwt);
    }

}