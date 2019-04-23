package com.evil.cbs.ticket.validator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TripAttendRequest {
    private String encryptedOrderId;
}
