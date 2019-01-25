package com.evil.cbs.service.impl;

import com.evil.cbs.common.UserNotAuthenticatedException;
import com.evil.cbs.security.jwt.JwtProvider;
import com.evil.cbs.service.AuthenticationService;
import com.evil.cbs.web.jwt.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponse authenticate(String username, String password) throws UserNotAuthenticatedException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Optional.of(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities())).orElseThrow(() -> new UserNotAuthenticatedException("User " + username + " not authenticated!"));
    }
}
