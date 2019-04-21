package com.evil.cbs.ticket.validator.repository;

import com.evil.cbs.ticket.validator.domain.TripAttendAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripAttendAttemptRepository extends JpaRepository<TripAttendAttempt, Long> {

    @Query("select taa from TripAttendAttempt taa where taa.ticket.id = :ticketId")
    List<TripAttendAttempt> findAttendAttemptsForTicket(@Param("ticketId") Long ticketId);
}
