package com.evil.cbs_ticketvalidator.data;


import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private String username;
}
