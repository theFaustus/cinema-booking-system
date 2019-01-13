package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "halls", schema = "cbs")
public class Hall extends AbstractEntity{
    @Column(name = "name", unique = true)
    private String name;
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats = new HashSet<>();
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<MovieSession> movieSessions = new HashSet<>();

    public void addMovieSession(MovieSession movieSession){
        movieSessions.add(movieSession);
    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }

    public static final class HallBuilder {
        private String name;
        private Set<Seat> seats = new HashSet<>();
        private Set<MovieSession> movieSessions = new HashSet<>();

        private HallBuilder() {
        }

        public static HallBuilder aHall() {
            return new HallBuilder();
        }

        public HallBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HallBuilder seats(Set<Seat> seats) {
            this.seats = seats;
            return this;
        }

        public HallBuilder movieSessions(Set<MovieSession> movieSessions) {
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
