package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Email;
import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.domain.Order;
import com.evil.cbs.domain.Seat;
import com.evil.cbs.domain.SeatStatus;
import com.evil.cbs.domain.Ticket;
import com.evil.cbs.domain.TicketStatus;
import com.evil.cbs.domain.TicketType;
import com.evil.cbs.domain.User;
import com.evil.cbs.domain.common.MovieSessionNotFoundException;
import com.evil.cbs.domain.common.SeatAlreadyBookedException;
import com.evil.cbs.repository.MovieSessionRepository;
import com.evil.cbs.repository.OrderRepository;
import com.evil.cbs.service.EmailService;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
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
    private final OrderRepository orderRepository;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

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
        User userByUsername = userService.findUserByUsername(bookedMovieDTO.getUsername());
        Ticket ticket = Ticket.builder()
                .ticketStatus(TicketStatus.NOT_USED)
                .ticketType(TicketType.SIMPLE)
                .bookedSeat(seatBySeatNumber)
                .movieSession(movieSessionById)
                .user(userByUsername)
                .build();
        ticketService.saveTicket(ticket);

        Order order = Order.builder()
                .ticket(ticket)
                .build();
        orderRepository.save(order);
        File ticketFile = ticketService.createTicket(order, "mail/ticket");
        emailService.sendEmail(buildEmail(ticketFile, renderEmailBody(order), userByUsername.getEmail()));
        return TicketDTO.from(ticket);
    }

    private Email buildEmail(File ticket, String emailBody, String email) {
        return Email.builder()
                .fromEmail(buildProperties().getProperty("email"))
                .toEmail(email)
                .message(emailBody)
                .host(buildProperties().getProperty("host"))
                .password(buildProperties().getProperty("password"))
                .port(buildProperties().getProperty("port"))
                .attachedFiles(Collections.singletonList(ticket.getAbsolutePath()))
                .subject("CBS - Ticket Reservation")
                .build();
    }

    private String renderEmailBody(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process("mail/email", context);
    }

    private Properties buildProperties() {
        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/config/email-config.properties"));
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
