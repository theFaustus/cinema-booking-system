package com.evil.cbs.web.rest;

import com.evil.cbs.domain.User;
import com.evil.cbs.service.AuthenticationService;
import com.evil.cbs.web.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationResource {
    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity authenticate(@RequestBody LoginDTO loginDTO){
        Optional<User> user = authenticationService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        if(user.isPresent()){
            log.info("Successfully logged user : " + loginDTO.getEmail());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        log.error("Failed to authenticate user : " + loginDTO.getEmail());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
