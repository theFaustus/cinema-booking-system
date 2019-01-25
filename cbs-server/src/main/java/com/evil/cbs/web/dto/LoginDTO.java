package com.evil.cbs.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDTO {
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30)
    private String username;
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30)
    private String password;
}
