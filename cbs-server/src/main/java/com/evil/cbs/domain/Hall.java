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
@ToString(exclude = "movieSessions")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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


}
