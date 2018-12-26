package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Movie;
import com.evil.cbs.repository.MovieRepository;
import com.evil.cbs.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie saveMovie(Movie m) {
        return movieRepository.save(m);
    }

    @Override
    public Movie findMovieByName(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
