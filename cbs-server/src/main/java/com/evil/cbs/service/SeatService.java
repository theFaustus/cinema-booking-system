package com.evil.cbs.service;

import com.evil.cbs.domain.Seat;

import java.util.List;

public interface SeatService {
    Seat saveSeat(Seat s);

    Seat findSeatBySeatNumber(String seatNumber);

    List<Seat> findAll();
}
