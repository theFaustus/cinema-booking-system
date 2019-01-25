package com.evil.cbs.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
public class UserDTO {
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30)
    private String username;

    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
    private String userPassword;

    @NotNull
    @Pattern(regexp = "0\\(\\d{2}\\)-\\d{3}-\\d{3}")
    private String telephoneNumber;

    @Min(0)
    @Max(1)
    private Integer enabled;
}
