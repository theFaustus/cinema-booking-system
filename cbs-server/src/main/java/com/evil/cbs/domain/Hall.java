package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "halls", schema = "cbs")
public class Hall extends AbstractEntity{
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieSession> movieSessions = new ArrayList<>();

    public void addMovieSession(MovieSession movieSession){
        movieSessions.add(movieSession);
    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }

    public static final class HallBuilder {
        private String name;
        private List<Seat> seats = new ArrayList<>();
        private List<MovieSession> movieSessions = new ArrayList<>();

        private HallBuilder() {
        }

        public static HallBuilder aHall() {
            return new HallBuilder();
        }

        public HallBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HallBuilder seats(List<Seat> seats) {
            this.seats = seats;
            return this;
        }

        public HallBuilder movieSessions(List<MovieSession> movieSessions) {
            this.movieSessions = movieSessions;
            return this;
        }

        public Hall build() {
            Hall hall = new Hall();
            hall.setName(name);
            hall.setSeats(seats);
            hall.setMovieSessions(movieSessions);
            return hall;
        }
    }
}
