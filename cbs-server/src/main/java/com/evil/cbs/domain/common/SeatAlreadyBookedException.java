package com.evil.cbs.domain.common;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException(String s) {
        super(s);
    }
}
