/*
 * Copyright (c)  IInfo 2021.
 */

package com.i1nfo.icb.utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
    public static @NotNull String calcMD5(@NotNull String m) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] result = md.digest(m.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, result).toString(16);
    }
}
