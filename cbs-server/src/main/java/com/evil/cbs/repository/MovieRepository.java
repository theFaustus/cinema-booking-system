package com.evil.cbs.repository;

import com.evil.cbs.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select distinct m from Movie m join fetch m.actors join fetch m.directors where m.name = :name")
    Optional<Movie> findByName(@Param("name") String name);
    @Query(value = "select distinct m from Movie m join fetch m.actors join fetch m.directors where m.id = :id")
    Optional<Movie> findById(@Param("id") Long id);
    @Query(value = "select distinct m from Movie m join fetch m.actors join fetch m.directors")
    List<Movie> findAll();
}
