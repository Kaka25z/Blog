package com.blog.server.utils;

public class JacksonException extends RuntimeException{

    public JacksonException(String message, Exception e) {
        super(message, e);
    }
}
