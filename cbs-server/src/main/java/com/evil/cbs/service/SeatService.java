package com.evil.cbs.service;

import com.evil.cbs.domain.Seat;

import java.util.List;

public interface SeatService {
    Seat saveSeat(Seat s);

    Seat findBySeatNumber(String seatNumber);

    List<Seat> findAll();

    List<Seat> findAllByHallId(Long hallId);
}
