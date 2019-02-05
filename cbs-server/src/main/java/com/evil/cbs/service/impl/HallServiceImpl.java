package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.domain.Seat;
import com.evil.cbs.domain.SeatStatus;
import com.evil.cbs.repository.HallRepository;
import com.evil.cbs.service.HallService;
import com.evil.cbs.domain.common.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;

    @Override
    public Hall saveHall(Hall h) {
        return hallRepository.save(h);
    }

    @Override
    public Hall findHallByName(String name) {
        return hallRepository.findByName(name).orElseThrow(HallNotFoundException::new);
    }

    @Override
    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    @Override
    public Hall findById(Long hallId) {
        return hallRepository.findById(hallId).orElseThrow(HallNotFoundException::new);
    }

    @Override
    public void deleteById(Long hallId) {
        hallRepository.deleteById(hallId);
    }

    @Override
    public Hall saveHall(Hall hall, Integer numberOfSeats) {
        for(int i = 0; i < numberOfSeats; i++){
            Seat s = Seat.builder()
                    .seatStatus(SeatStatus.FREE)
                    .price(150)
                    .hall(hall)
                    .seatNumber("A" + i)
                    .build();
            hall.addSeat(s);
        }
        hallRepository.save(hall);
        return hall;
    }
}
