/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.exception;

public class NotAllowedException extends RuntimeException {

    private final Type type;

    public NotAllowedException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        ADMIN, USER
    }
}
