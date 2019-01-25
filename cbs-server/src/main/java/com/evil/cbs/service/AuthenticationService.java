package com.evil.cbs.service;

import com.evil.cbs.common.UserNotAuthenticatedException;
import com.evil.cbs.web.jwt.JwtResponse;

public interface AuthenticationService {
    JwtResponse authenticate(String username, String password) throws UserNotAuthenticatedException;

}
