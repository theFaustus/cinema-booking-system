package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Movie;
import com.evil.cbs.repository.MovieRepository;
import com.evil.cbs.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService movieService;
    private Movie movie;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        movieService = new MovieServiceImpl(movieRepository);

        movie = Movie.builder()
                .name("Pulp fiction")
                .actors(Collections.emptySet())
                .directors(Collections.emptySet())
                .description("Nice Movie")
                .imdbRating("9.2")
                .movieDuration(Duration.ofMinutes(90))
                .build();
        movie.setId(1L);

        when(movieRepository.findByName(movie.getName())).thenReturn(Optional.of(movie));
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
    }

    @Test
    public void testFindMovieByName() {
        Movie movieByName = movieService.findMovieByName(movie.getName());
        verify(movieRepository).findByName(movie.getName());
        assertThat(movieByName).isEqualTo(movie);
    }

    @Test
    public void testFindById() {
        Movie movieById = movieService.findById(movie.getId());
        verify(movieRepository).findById(movie.getId());
        assertThat(movieById).isEqualTo(movie);
    }
}