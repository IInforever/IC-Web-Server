/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
    public static String calcMD5(String m) {
        if (m == null)
            return null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm: MD5");
        }
        byte[] result = md.digest(m.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, result).toString(16);
    }
}
