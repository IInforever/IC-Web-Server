/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;

@Component
public class RandomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        byte[] bytes = new byte[4];
        new Random().nextBytes(bytes);
        return new BigInteger(1, bytes).longValue();
    }

}
