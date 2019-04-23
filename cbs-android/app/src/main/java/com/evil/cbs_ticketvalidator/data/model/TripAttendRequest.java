package com.evil.cbs_ticketvalidator.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripAttendRequest {
    private String encryptedOrderId;
}
