package com.evil.cbs.web.rest;

import com.evil.cbs.domain.User;
import com.evil.cbs.service.AuthenticationService;
import com.evil.cbs.web.form.LoginFormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/authentication")
public class AuthenticationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationResource.class);

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity authenticate(@RequestBody LoginFormBean loginFormBean){
        Optional<User> user = authenticationService.authenticate(loginFormBean.getEmail(), loginFormBean.getPassword());
        if(user.isPresent()){
            LOGGER.info("Successfully logged user : " + loginFormBean.getEmail());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        LOGGER.error("Failed to authenticate user : " + loginFormBean.getEmail());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
