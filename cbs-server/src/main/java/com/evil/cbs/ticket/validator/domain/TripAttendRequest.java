package com.evil.cbs.ticket.validator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
public class TripAttendRequest {
    private String encryptedOrderId;
}
