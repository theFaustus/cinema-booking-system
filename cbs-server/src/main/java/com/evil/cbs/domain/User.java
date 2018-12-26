package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "users", schema = "cbs")
public class User extends AbstractEntity {
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


    public static final class UserBuilder {
        private String email;
        private String password;
        private String role;
        private int enabled = 1;
        private String firstName;
        private String lastName;
        private String telephoneNumber;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserBuilder enabled(int enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder telephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public User build() {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role);
            user.setEnabled(enabled);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTelephoneNumber(telephoneNumber);
            return user;
        }
    }
}
