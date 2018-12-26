package com.evil.cbs.web.rest;

import com.evil.cbs.domain.User;
import com.evil.cbs.service.UserService;
import com.evil.cbs.web.form.RegisterUserFormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{user-id}/booked-tickets")
    public List<String> getBookedTickets() {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@Valid @RequestBody RegisterUserFormBean registerUserFormBean, BindingResult bindingResult){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        else {
            try {
                User u = userService.saveUser(registerUserFormBean);
                return  ResponseEntity.status(HttpStatus.CREATED).body(u);
            } catch (Exception e){
                LOGGER.error("User not registered!", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @RequestMapping(value = "/{userEmail}/enable", method = RequestMethod.POST)
    public ResponseEntity enableUser(@PathVariable String userEmail){
        try {
            userService.enableUser(userEmail);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @RequestMapping(value = "/{userEmail}/disable", method = RequestMethod.POST)
    public ResponseEntity disableUser(@PathVariable String userEmail){
        try {
            userService.disableUser(userEmail);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.findAll();
    }

}
