/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.exception;

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
