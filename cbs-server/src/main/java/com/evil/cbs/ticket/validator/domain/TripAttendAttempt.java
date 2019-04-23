package com.evil.cbs.ticket.validator.domain;

import com.evil.cbs.domain.AbstractEntity;
import com.evil.cbs.domain.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.OneToOne;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class TripAttendAttempt extends AbstractEntity {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate attendDate = LocalDate.now();

    @OneToOne
    @JsonIgnore
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private AttendStatus attemptStatus;
}
