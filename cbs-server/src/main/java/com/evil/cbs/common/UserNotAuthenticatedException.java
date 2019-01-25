package com.evil.cbs.common;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
