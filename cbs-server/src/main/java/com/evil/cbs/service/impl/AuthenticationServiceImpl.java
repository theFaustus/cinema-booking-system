package com.evil.cbs.service.impl;

import com.evil.cbs.domain.User;
import com.evil.cbs.repository.UserRepository;
import com.evil.cbs.service.AuthenticationService;
import com.evil.cbs.web.rest.AuthenticationResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

//    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public Optional<User> authenticate(String email, String password) {
//        try {
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
//                    = new UsernamePasswordAuthenticationToken(email, password);
//            Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//            SecurityContext context = SecurityContextHolder.getContext();
//            context.setAuthentication(auth);
//            User userByEmail = userRepository.findUserByEmail(email);
//            return Optional.of(userByEmail);
//        } catch (Exception e) {
//            log.error("User not authenticated!", e);
//            return Optional.empty();
//        }
        return Optional.empty();
    }
}
