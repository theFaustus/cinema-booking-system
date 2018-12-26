package com.evil.cbs.service;

import com.evil.cbs.domain.Movie;

import java.util.List;

public interface MovieService {
    Movie saveMovie(Movie m);

    Movie findMovieByName(String name);

    List<Movie> findAll();
}
