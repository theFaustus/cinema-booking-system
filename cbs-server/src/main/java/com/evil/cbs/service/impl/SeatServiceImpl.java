package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Seat;
import com.evil.cbs.repository.SeatRepository;
import com.evil.cbs.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public Seat saveSeat(Seat s) {
        return seatRepository.save(s);
    }

    @Override
    public Seat findSeatBySeatNumber(String seatNumber) {
        return seatRepository.findBySeatNumber(seatNumber);
    }

    @Override
    public List<Seat> findAll() {
        return seatRepository.findAll();
    }
}
