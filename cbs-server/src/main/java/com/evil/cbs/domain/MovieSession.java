package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
