package com.evil.cbs.service.impl;

import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.domain.Seat;
import com.evil.cbs.domain.SeatStatus;
import com.evil.cbs.domain.Ticket;
import com.evil.cbs.domain.TicketStatus;
import com.evil.cbs.domain.TicketType;
import com.evil.cbs.domain.common.MovieSessionNotFoundException;
import com.evil.cbs.domain.common.SeatAlreadyBookedException;
import com.evil.cbs.repository.MovieSessionRepository;
import com.evil.cbs.service.HallService;
import com.evil.cbs.service.MovieService;
import com.evil.cbs.service.MovieSessionService;
import com.evil.cbs.service.SeatService;
import com.evil.cbs.service.TicketService;
import com.evil.cbs.service.UserService;
import com.evil.cbs.web.dto.BookedMovieDTO;
import com.evil.cbs.web.dto.MovieSessionDTO;
import com.evil.cbs.web.dto.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<MovieSessionDTO> findMovieSessionByMovieId(Long movieId) {
        return movieSessionRepository.getDistinctByMovieId(movieId).stream().map(
                MovieSessionDTO::from).collect(Collectors.toList());
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
        Seat seatBySeatNumber = seatService.findBySeatNumberAndHallId(bookedMovieDTO.getSeatNumber(), movieSessionById.getHall().getId());
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
                .user(userService.findUserByUsername(bookedMovieDTO.getUsername()))
                .build();
        ticketService.saveTicket(ticket);

        return TicketDTO.from(ticket);
    }


}
