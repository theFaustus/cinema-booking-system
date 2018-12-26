package com.evil.cbs.service;

import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.domain.Ticket;
import com.evil.cbs.web.dto.BookedMovieDTO;
import com.evil.cbs.web.dto.MovieSessionDTO;

import java.util.List;

public interface MovieSessionService {
    MovieSession saveMovieSession(MovieSessionDTO movieSessionDTO);

    List<MovieSession> findMovieSessionByMovieId(Long movieId);

    MovieSession findById(Long movieSessionId);

    void deleteById(Long movieSessionId);

    Ticket bookMovie(BookedMovieDTO bookedMovieDTO);
}
