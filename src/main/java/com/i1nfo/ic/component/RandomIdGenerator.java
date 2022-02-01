/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;

@Component
public class RandomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        // Generate 4 Byte size unsigned integer ID
        byte[] bytes = new byte[4];
        new Random().nextBytes(bytes);
        return new BigInteger(1, bytes).longValue();
    }

}
