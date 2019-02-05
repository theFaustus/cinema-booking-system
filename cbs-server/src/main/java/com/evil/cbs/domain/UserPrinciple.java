package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@ToString
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;
    @JsonIgnore
    private String password;

    private String telephoneNumber;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple build(User user) {
        return UserPrincipleBuilder.anUserPrinciple()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .telephoneNumber(user.getTelephoneNumber())
                .password(user.getPassword())
                .authorities(Arrays.asList(new SimpleGrantedAuthority(user.getRole())))
                .build();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }


    public static final class UserPrincipleBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String password;
        private String telephoneNumber;
        private Collection<? extends GrantedAuthority> authorities;

        private UserPrincipleBuilder() {
        }

        public static UserPrincipleBuilder anUserPrinciple() {
            return new UserPrincipleBuilder();
        }

        public UserPrincipleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserPrincipleBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserPrincipleBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserPrincipleBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserPrincipleBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserPrincipleBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserPrincipleBuilder telephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public UserPrincipleBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserPrinciple build() {
            return new UserPrinciple(id, firstName, lastName, username, email, password, telephoneNumber, authorities);
        }
    }
}
