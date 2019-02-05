package com.evil.cbs.web.rest;

import com.evil.cbs.domain.User;
import com.evil.cbs.service.AuthenticationService;
import com.evil.cbs.service.UserService;
import com.evil.cbs.web.dto.LoginDTO;
import com.evil.cbs.web.dto.UserDTO;
import com.evil.cbs.web.jwt.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evil.cbs.domain.common.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationResource {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) throws UserNotAuthenticatedException {
        JwtResponse response = null;
        response = authenticationService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {

        if (userService.existsByUsername(userDTO.getUsername())) {
            return new ResponseEntity<>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(userDTO.getEmail())) {
            return new ResponseEntity<>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        User u = userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }
}
