package com.evil.cbs_ticketvalidator.data.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripAttendAttemptHistory {
    private List<TripAttendAttempt> attempts = new ArrayList<>();
}

