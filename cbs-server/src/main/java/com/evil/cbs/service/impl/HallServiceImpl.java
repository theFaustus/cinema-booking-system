package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.repository.HallRepository;
import com.evil.cbs.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallServiceImpl implements HallService {
    @Autowired
    private HallRepository hallRepository;

    @Override
    public Hall saveHall(Hall h) {
        return hallRepository.save(h);
    }

    @Override
    public Hall findHallByName(String name) {
        return hallRepository.findByName(name);
    }

    @Override
    public List<Hall> findAll() {
        return hallRepository.findAll();
    }
}
