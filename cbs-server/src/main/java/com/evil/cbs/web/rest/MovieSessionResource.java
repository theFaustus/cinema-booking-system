package com.evil.cbs.web.rest;

import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.domain.Movie;
import com.evil.cbs.domain.Ticket;
import com.evil.cbs.service.HallService;
import com.evil.cbs.service.MovieSessionService;
import com.evil.cbs.web.dto.BookedMovieDTO;
import com.evil.cbs.web.dto.MovieSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/movie-sessions")
@RequiredArgsConstructor
@Slf4j
public class MovieSessionResource {

    private final MovieSessionService movieSessionService;

    @PostMapping
    public ResponseEntity<MovieSession> addMovie(@Valid @RequestBody MovieSessionDTO movieSessionDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSessionService.saveMovieSession(movieSessionDTO));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<List<MovieSession>> getAllMovieSessionsByMovieId(@PathVariable("movieId") Long movieId){
        return ResponseEntity.status(HttpStatus.OK).body(movieSessionService.findMovieSessionByMovieId(movieId));
    }

    @GetMapping("/{movieSessionId}/")
    public ResponseEntity<MovieSession> getMovieSessionById(@PathVariable("movieSessionId") Long movieSessionId){
        return ResponseEntity.status(HttpStatus.OK).body(movieSessionService.findById(movieSessionId));
    }

    @DeleteMapping("/{movieSessionId}/")
    public ResponseEntity<Movie> deleteMovieSessionById(@PathVariable("movieSessionId") Long movieSessionId){
        movieSessionService.deleteById(movieSessionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/{movieSessionId}/booked-movies")
    public ResponseEntity<Ticket> bookMovie(@Valid @RequestBody BookedMovieDTO bookedMovieDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSessionService.bookMovie(bookedMovieDTO));
    }

}
