package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "users", schema = "cbs")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends AbstractEntity {
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
