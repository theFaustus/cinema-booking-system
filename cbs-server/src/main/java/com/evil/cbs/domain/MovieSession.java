package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@EqualsAndHashCode(exclude = "hall", callSuper = true)
@Entity
@Table(name = "movie_sessions", schema = "cbs")
@NamedEntityGraph(name = "MovieSession.hallWithSeatsAndMovie", attributeNodes = {
        @NamedAttributeNode(value = "hall", subgraph = "hall.seats"),
        @NamedAttributeNode(value = "movie", subgraph = "movie.directorsAndActors")},
        subgraphs = {
                @NamedSubgraph(name = "hall.seats", attributeNodes = @NamedAttributeNode(value = "seats")),
                @NamedSubgraph(name = "movie.directorsAndActors", attributeNodes = {
                        @NamedAttributeNode(value = "directors"),
                        @NamedAttributeNode(value = "actors")}),
        })
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MovieSession extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Hall hall;
    @OneToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @Column(name = "show_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime showTime;


}
