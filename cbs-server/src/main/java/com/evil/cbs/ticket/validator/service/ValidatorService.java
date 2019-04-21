package com.evil.cbs.ticket.validator.service;


import com.evil.cbs.ticket.validator.domain.TripAttendRequest;
import com.evil.cbs.ticket.validator.domain.ValidatorVerdict;

public interface ValidatorService {
    ValidatorVerdict isAttenderAllowedIn(TripAttendRequest request);
}
