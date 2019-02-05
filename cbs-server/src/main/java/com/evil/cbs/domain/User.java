package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "users", schema = "cbs")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class User extends AbstractEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String role;
    @JsonIgnore
    private int enabled = 1;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "telephone_number")
    private String telephoneNumber;


}
