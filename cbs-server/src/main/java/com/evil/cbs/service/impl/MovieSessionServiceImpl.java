package com.evil.cbs.service.impl;

import com.evil.cbs.domain.*;
import com.evil.cbs.repository.MovieSessionRepository;
import com.evil.cbs.service.*;
import com.evil.cbs.web.common.MovieSessionNotFoundException;
import com.evil.cbs.web.dto.BookedMovieDTO;
import com.evil.cbs.web.dto.MovieSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {

    private final HallService hallService;
    private final MovieService movieService;
    private final SeatService seatService;
    private final UserService userService;
    private final TicketService ticketService;
    private final MovieSessionRepository movieSessionRepository;

    @Override
    public MovieSession saveMovieSession(MovieSessionDTO movieSessionDTO) {
        MovieSession movieSession = MovieSession.MovieSessionBuilder.aMovieSession()
                .hall(hallService.findById(movieSessionDTO.getHallId()))
                .movie(movieService.findById(movieSessionDTO.getMovieId()))
                .showTime(movieSessionDTO.getShowTime())
                .build();
        return movieSessionRepository.save(movieSession);
    }

    @Override
    public List<MovieSession> findMovieSessionByMovieId(Long movieId) {
        return movieSessionRepository.findByMovieId(movieId);
    }

    @Override
    public MovieSession findById(Long movieSessionId) {
        return movieSessionRepository.findById(movieSessionId).orElseThrow(MovieSessionNotFoundException::new);
    }

    @Override
    public void deleteById(Long movieSessionId) {
        movieSessionRepository.deleteById(movieSessionId);
    }

    @Override
    public Ticket bookMovie(BookedMovieDTO bookedMovieDTO) {
        MovieSession movieSessionById = movieSessionRepository.findById(bookedMovieDTO.getMovieSessionId())
                .orElseThrow(MovieSessionNotFoundException::new);
        Seat seatBySeatNumber = seatService.findSeatBySeatNumber(bookedMovieDTO.getSeatNumber());
        seatBySeatNumber.setSeatStatus(SeatStatus.BOOKED);
        seatService.saveSeat(seatBySeatNumber);
        Ticket ticket = Ticket.TicketBuilder.aTicket()
                .ticketStatus(TicketStatus.NOT_USED)
                .ticketType(TicketType.SIMPLE)
                .bookedSeat(seatBySeatNumber)
                .movieSession(movieSessionById)
                .user(userService.findById(bookedMovieDTO.getUserId()))
                .build();
        ticketService.saveTicket(ticket);



        return null;
    }


}
