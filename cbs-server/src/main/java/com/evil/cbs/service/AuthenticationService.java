package com.evil.cbs.service;

import com.evil.cbs.domain.User;

import java.util.Optional;

public interface AuthenticationService {
    Optional<User> authenticate(String email, String password);

}
