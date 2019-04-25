package com.evil.cbs_ticketvalidator.data.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripAttendAttempt {
    private String attendDate;
    private String attemptStatus;
}
