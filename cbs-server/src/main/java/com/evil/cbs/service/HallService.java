package com.evil.cbs.service;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.domain.Hall;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface HallService {
    Hall saveHall(Hall h);

    Hall findHallByName(String name);

    List<Hall> findAll();

    Hall findById(Long hallId);

    void deleteById(Long hallId);

    Hall saveHall(Hall hall, Integer numberOfSeats);
}
