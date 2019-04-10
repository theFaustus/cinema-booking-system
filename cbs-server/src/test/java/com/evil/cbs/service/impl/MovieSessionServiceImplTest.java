package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.domain.Movie;
import com.evil.cbs.domain.MovieSession;
import com.evil.cbs.repository.MovieRepository;
import com.evil.cbs.repository.MovieSessionRepository;
import com.evil.cbs.repository.OrderRepository;
import com.evil.cbs.service.EmailService;
import com.evil.cbs.service.HallService;
import com.evil.cbs.service.MovieService;
import com.evil.cbs.service.MovieSessionService;
import com.evil.cbs.service.SeatService;
import com.evil.cbs.service.TicketService;
import com.evil.cbs.service.UserService;
import com.evil.cbs.web.dto.MovieSessionDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.TemplateEngine;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MovieSessionServiceImplTest {

    @Mock
    private MovieSessionRepository movieSessionRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private HallService hallService;
    @Mock
    private MovieService movieService;
    @Mock
    private SeatService seatService;
    @Mock
    private UserService userService;
    @Mock
    private TicketService ticketService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private EmailService emailService;


    private MovieSessionService movieSessionService;
    private Movie movie;
    private Hall hall;
    private MovieSession movieSession;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        movieSessionService = new MovieSessionServiceImpl(hallService,
                movieService,
                seatService,
                userService,
                ticketService,
                movieSessionRepository,
                orderRepository,
                templateEngine,
                emailService);

        movie = Movie.builder()
                .name("Pulp fiction")
                .actors(Collections.emptySet())
                .directors(Collections.emptySet())
                .description("Nice Movie")
                .imdbRating("9.2")
                .movieDuration(Duration.ofMinutes(90))
                .build();
        movie.setId(1L);

        hall = Hall.builder()
                .name("Hall 1")
                .build();
        hall.setId(1L);

        movieSession = MovieSession.builder()
                .hall(hall)
                .movie(movie)
                .showTime(LocalDateTime.of(2019, Month.JANUARY, 12, 12, 12))
                .build();
        movieSession.setId(1L);

        when(movieSessionRepository.getDistinctById(movieSession.getId())).thenReturn(Optional.ofNullable(movieSession));
        when(movieSessionRepository.getDistinctByMovieId(movie.getId())).thenReturn(Collections.singletonList(movieSession));
    }

    @Test
    public void testFindMovieSessionByMovieId() {
        List<MovieSessionDTO> movieSessionByMovieId = movieSessionService.findMovieSessionByMovieId(movie.getId());
        verify(movieSessionRepository).getDistinctByMovieId(movie.getId());
        assertThat(movieSessionByMovieId.get(0).getHallId()).isEqualTo(movieSession.getHall().getId());
        assertThat(movieSessionByMovieId.get(0).getMovieId()).isEqualTo(movieSession.getMovie().getId());
        assertThat(movieSessionByMovieId.get(0).getShowTime()).isEqualTo(movieSession.getShowTime());
    }

    @Test
    public void testFindById() {
        MovieSession movieSessionById = movieSessionService.findById(movieSession.getId());
        verify(movieSessionRepository).getDistinctById(movieSession.getId());
        assertThat(movieSessionById.getHall().getId()).isEqualTo(movieSession.getHall().getId());
        assertThat(movieSessionById.getMovie().getId()).isEqualTo(movieSession.getMovie().getId());
        assertThat(movieSessionById.getShowTime()).isEqualTo(movieSession.getShowTime());
    }

}