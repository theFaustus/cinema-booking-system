package com.evil.cbs.ticket.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorVerdict {
    private boolean isAllowedToEnter;
    private String encryptedOrderId;
    private String error;
}
