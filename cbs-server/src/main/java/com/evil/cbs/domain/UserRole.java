package com.evil.cbs.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String value;

    UserRole(String role) {
        this.value = role;
    }
}