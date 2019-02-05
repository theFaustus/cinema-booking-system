package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Movie;
import com.evil.cbs.repository.MovieRepository;
import com.evil.cbs.service.MovieService;
import com.evil.cbs.domain.common.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie saveMovie(Movie m) {
        return movieRepository.save(m);
    }

    @Override
    public Movie findMovieByName(String name) {
        return movieRepository.findByName(name).orElseThrow(MovieNotFoundException::new);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(MovieNotFoundException::new);
    }

    @Override
    public void deleteById(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
