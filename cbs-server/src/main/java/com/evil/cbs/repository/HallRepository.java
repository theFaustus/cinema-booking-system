package com.evil.cbs.repository;

import com.evil.cbs.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    @Query("select h from Hall h join fetch h.seats where h.name = :name")
    Optional<Hall> findByName(@Param("name") String name);

    @Query("select h from Hall h join fetch h.seats where h.id = :id")
    Optional<Hall> findById(@Param("id") Long id);
}
