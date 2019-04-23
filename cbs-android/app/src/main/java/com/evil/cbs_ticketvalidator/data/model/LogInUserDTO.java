package com.evil.cbs_ticketvalidator.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogInUserDTO {
    private String username;
    private String password;
}
