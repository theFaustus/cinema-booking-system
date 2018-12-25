package com.evil.cbs.service.impl;

import com.evil.cbs.domain.User;
import com.evil.cbs.repository.UserRepository;
import com.evil.cbs.service.AuthenticationService;
import com.evil.cbs.web.rest.AuthenticationResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> authenticate(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(email, password);
            Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);
            User userByEmail = userRepository.findUserByEmail(email);
            return Optional.of(userByEmail);
        } catch (Exception e) {
            LOGGER.error("User not authenticated!", e);
            return Optional.empty();
        }
    }
}
