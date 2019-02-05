package com.evil.cbs.service;

import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.domain.Ticket;
import com.evil.cbs.web.dto.BookedMovieDTO;
import com.evil.cbs.web.dto.MovieSessionDTO;
import com.evil.cbs.web.dto.TicketDTO;

import java.util.List;

public interface MovieSessionService {
    MovieSession saveMovieSession(MovieSessionDTO movieSessionDTO);

    List<MovieSessionDTO> findMovieSessionByMovieId(Long movieId);

    MovieSession findById(Long movieSessionId);

    void deleteById(Long movieSessionId);

    TicketDTO bookMovie(BookedMovieDTO bookedMovieDTO);
}
