package com.evil.cbs.repository;

import com.evil.cbs.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findBySeatNumber(String seatNumber);
    List<Seat> findAllByHallId(Long hallId);
}
