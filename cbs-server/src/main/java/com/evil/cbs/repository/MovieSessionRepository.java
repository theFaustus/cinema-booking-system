package com.evil.cbs.repository;

import com.evil.cbs.domain.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
    List<MovieSession> findByMovieId(Long movieId);
}
