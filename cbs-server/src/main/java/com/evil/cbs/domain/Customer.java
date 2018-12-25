package com.evil.cbs.domain;

import com.evil.cbs.service.util.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "customers", schema = "cbs")
public class Customer extends User {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public static final class CustomerBuilder {
        private List<Ticket> tickets = new ArrayList<>();
        private String email;
        private String password;
        private String role;
        private int enabled = 1;
        private String firstName;
        private String lastName;
        private String telephoneNumber;

        private CustomerBuilder() {
        }

        public static CustomerBuilder aCustomer() {
            return new CustomerBuilder();
        }

        public CustomerBuilder tickets(List<Ticket> tickets) {
            this.tickets = tickets;
            return this;
        }

        public CustomerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CustomerBuilder role(String role) {
            this.role = role;
            return this;
        }

        public CustomerBuilder enabled(int enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomerBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder telephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setRole(UserRole.USER_ROLE.getValue());
            customer.setTickets(tickets);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setRole(role);
            customer.setEnabled(enabled);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setTelephoneNumber(telephoneNumber);
            return customer;
        }
    }
}
