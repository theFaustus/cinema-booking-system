package com.evil.cbs.service.impl;

import com.evil.cbs.domain.*;
import com.evil.cbs.repository.MovieSessionRepository;
import com.evil.cbs.service.*;
import com.evil.cbs.web.dto.BookedMovieDTO;
import com.evil.cbs.web.dto.MovieSessionDTO;
import com.evil.cbs.web.dto.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.evil.cbs.domain.common.*;

import javax.transaction.Transactional;
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
        MovieSession movieSession = MovieSession.builder()
                .hall(hallService.findById(movieSessionDTO.getHallId()))
                .movie(movieService.findById(movieSessionDTO.getMovieId()))
                .showTime(movieSessionDTO.getShowTime())
                .build();
        return movieSessionRepository.save(movieSession);
    }

    @Override
    public List<MovieSession> findMovieSessionByMovieId(Long movieId) {
        return movieSessionRepository.getDistinctByMovieId(movieId);
    }

    @Override
    public MovieSession findById(Long movieSessionId) {
        return movieSessionRepository.getDistinctById(movieSessionId).orElseThrow(MovieSessionNotFoundException::new);
    }

    @Override
    public void deleteById(Long movieSessionId) {
        movieSessionRepository.deleteById(movieSessionId);
    }

    @Override
    @Transactional
    public TicketDTO bookMovie(BookedMovieDTO bookedMovieDTO) throws SeatAlreadyBookedException {
        MovieSession movieSessionById = movieSessionRepository.getDistinctById(bookedMovieDTO.getMovieSessionId())
                .orElseThrow(MovieSessionNotFoundException::new);
        Seat seatBySeatNumber = seatService.findSeatBySeatNumber(bookedMovieDTO.getSeatNumber());
        if(seatBySeatNumber.isBooked()){
            throw new SeatAlreadyBookedException("Seat already was booked!");
        }
        seatBySeatNumber.setSeatStatus(SeatStatus.BOOKED);
        seatService.saveSeat(seatBySeatNumber);
        Ticket ticket = Ticket.builder()
                .ticketStatus(TicketStatus.NOT_USED)
                .ticketType(TicketType.SIMPLE)
                .bookedSeat(seatBySeatNumber)
                .movieSession(movieSessionById)
                .user(userService.findById(bookedMovieDTO.getUserId()))
                .build();
        ticketService.saveTicket(ticket);

        return TicketDTO.from(ticket);
    }


}
