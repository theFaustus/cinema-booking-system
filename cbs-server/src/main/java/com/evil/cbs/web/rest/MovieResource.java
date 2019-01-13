package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Movie;
import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.service.MovieService;
import com.evil.cbs.service.MovieSessionService;
import com.evil.cbs.web.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/movies")
@Slf4j
public class MovieResource {

    private final MovieService movieService;
    private final MovieSessionService movieSessionService;

    @PostMapping
    public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie){
        try {
            movieService.saveMovie(movie);
        } catch (Exception e) {
            log.error("Movie not saved!", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll());
    }

    @GetMapping("/{movieId}/")
    public ResponseEntity<Movie> getMovieById(@PathVariable("movieId") Long movieId){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findById(movieId));
    }

    @GetMapping("/{movieId}/sessions")
    public ResponseEntity<List<MovieSession>> getAllMovieSessionsByMovieId(@PathVariable("movieId") Long movieId){
        return ResponseEntity.status(HttpStatus.OK).body(movieSessionService.findMovieSessionByMovieId(movieId));
    }

    @DeleteMapping("/{movieId}/")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable("movieId") Long movieId){
        movieService.deleteById(movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
