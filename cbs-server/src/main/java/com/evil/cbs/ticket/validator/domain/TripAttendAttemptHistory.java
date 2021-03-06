package com.evil.cbs.ticket.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripAttendAttemptHistory {
    private List<TripAttendAttempt> attempts = new ArrayList<>();
}
