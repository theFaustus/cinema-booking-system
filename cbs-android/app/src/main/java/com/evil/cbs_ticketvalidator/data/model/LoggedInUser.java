package com.evil.cbs_ticketvalidator.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Data
@AllArgsConstructor
public class LoggedInUser {
    private String userId;
    private String displayName;

}
