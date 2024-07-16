package com.cookswp.milkstore.exception;

import org.springframework.security.core.AuthenticationException;

public class UserInvisibilityException extends AuthenticationException {
    public UserInvisibilityException(String msg) {
        super(msg);
    }
}
