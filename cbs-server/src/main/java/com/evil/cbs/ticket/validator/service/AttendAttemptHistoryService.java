package com.evil.cbs.ticket.validator.service;


import com.evil.cbs.ticket.validator.domain.TripAttendAttemptHistory;

public interface AttendAttemptHistoryService {

    TripAttendAttemptHistory getAttendAttemptHistory(String encryptedOrderId);
}
