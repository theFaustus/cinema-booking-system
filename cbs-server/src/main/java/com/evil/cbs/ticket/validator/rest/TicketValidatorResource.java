package com.evil.cbs.ticket.validator.rest;

import com.evil.cbs.ticket.validator.domain.TripAttendRequest;
import com.evil.cbs.ticket.validator.domain.ValidatorVerdict;
import com.evil.cbs.ticket.validator.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/validation")
public class TicketValidatorResource {
    private final ValidatorService validatorService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<ValidatorVerdict> allowAttenderIn(@RequestBody TripAttendRequest request) {
        return ResponseEntity.ok(validatorService.isAttenderAllowedIn(request));
    }
}
