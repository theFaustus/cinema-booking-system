package com.evil.cbs.repository;

import com.evil.cbs.domain.MovieSession;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
    @EntityGraph(value = "MovieSession.hallWithSeatsAndMovie", type = EntityGraph.EntityGraphType.LOAD)
    List<MovieSession> getDistinctByMovieId(Long movieId);

    @EntityGraph(value = "MovieSession.hallWithSeatsAndMovie", type = EntityGraph.EntityGraphType.LOAD)
    Optional<MovieSession> getDistinctById(Long movieSessionId);
}
