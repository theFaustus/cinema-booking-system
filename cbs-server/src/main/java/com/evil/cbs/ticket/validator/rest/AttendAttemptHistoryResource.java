package com.evil.cbs.ticket.validator.rest;

import com.evil.cbs.ticket.validator.domain.TripAttendAttemptHistory;
import com.evil.cbs.ticket.validator.service.AttendAttemptHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/attend-attempt-history/")
public class AttendAttemptHistoryResource {

    private final AttendAttemptHistoryService historyService;

    @GetMapping(value = "{encryptedOrderId}", produces = "application/json")
    public ResponseEntity<TripAttendAttemptHistory> getAttendAttemptHistory(@PathVariable("encryptedOrderId") String orderId) {
        return ResponseEntity.ok(historyService.getAttendAttemptHistory(orderId));
    }
}
