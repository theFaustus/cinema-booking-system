package com.evil.cbs.web.rest;

import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.service.MovieSessionService;
import com.evil.cbs.web.dto.BookedMovieDTO;
import com.evil.cbs.web.dto.MovieSessionDTO;
import com.evil.cbs.web.dto.TicketDTO;
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

@RestController
@RequestMapping(value = "/v1/api/movie-sessions")
@RequiredArgsConstructor
@Slf4j
public class MovieSessionResource {

    private final MovieSessionService movieSessionService;

    @PostMapping
    public ResponseEntity<?> addMovieSession(@Valid @RequestBody MovieSessionDTO movieSessionDTO){
        MovieSession movieSession;
        movieSession = movieSessionService.saveMovieSession(movieSessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSession);
    }

    @GetMapping("/{movieSessionId}/")
    public ResponseEntity<MovieSession> getMovieSessionById(@PathVariable("movieSessionId") Long movieSessionId){
        return ResponseEntity.status(HttpStatus.OK).body(movieSessionService.findById(movieSessionId));
    }

    @DeleteMapping("/{movieSessionId}/")
    public ResponseEntity<MovieSession> deleteMovieSessionById(@PathVariable("movieSessionId") Long movieSessionId){
        movieSessionService.deleteById(movieSessionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/{movieSessionId}/booked-movies")
    public ResponseEntity<TicketDTO> bookMovie(@Valid @RequestBody BookedMovieDTO bookedMovieDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSessionService.bookMovie(bookedMovieDTO));
    }

}
