package com.evil.cbs_ticketvalidator.data.model;

import lombok.Data;

@Data
public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String telephoneNumber;
    private Integer enabled;
}
