package com.evil.cbs_ticketvalidator.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
}
