package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "movie_sessions", schema = "cbs")
public class MovieSession extends AbstractEntity{
    @ManyToOne
    private Hall hall;
    @OneToOne
    private Movie movie;
    @Column(name = "show_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime showTime;


    public static final class MovieSessionBuilder {
        private Hall hall;
        private Movie movie;
        private LocalDateTime showTime;

        private MovieSessionBuilder() {
        }

        public static MovieSessionBuilder aMovieSession() {
            return new MovieSessionBuilder();
        }

        public MovieSessionBuilder hall(Hall hall) {
            this.hall = hall;
            return this;
        }

        public MovieSessionBuilder movie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public MovieSessionBuilder showTime(LocalDateTime showTime) {
            this.showTime = showTime;
            return this;
        }

        public MovieSession build() {
            MovieSession movieSession = new MovieSession();
            movieSession.setHall(hall);
            movieSession.setMovie(movie);
            movieSession.setShowTime(showTime);
            return movieSession;
        }
    }
}
