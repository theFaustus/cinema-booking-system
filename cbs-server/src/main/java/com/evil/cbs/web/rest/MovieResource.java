package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Movie;
import com.evil.cbs.service.MovieService;
import com.evil.cbs.service.MovieSessionService;
import com.evil.cbs.web.dto.MovieSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        movieService.saveMovie(movie);
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
    public ResponseEntity<List<MovieSessionDTO>> getAllMovieSessionsByMovieId(@PathVariable("movieId") Long movieId){
        return ResponseEntity.status(HttpStatus.OK).body(movieSessionService.findMovieSessionByMovieId(movieId));
    }

    @DeleteMapping("/{movieId}/")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable("movieId") Long movieId){
        movieService.deleteById(movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
