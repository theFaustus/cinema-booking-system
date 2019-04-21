package com.evil.cbs.ticket.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorVerdict {
    private boolean isAllowedToEnter;
    private String encryptedOrderId;
    private String error;
}
