package com.evil.cbs.common;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException(String s) {
        super(s);
    }
}
