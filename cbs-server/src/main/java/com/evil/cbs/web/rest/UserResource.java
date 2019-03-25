package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Ticket;
import com.evil.cbs.domain.User;
import com.evil.cbs.service.TicketService;
import com.evil.cbs.service.UserService;
import com.evil.cbs.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
@Slf4j
public class UserResource {

    private final UserService userService;
    private final TicketService ticketService;

    @GetMapping("/{userId}/booked-tickets")
    public ResponseEntity<List<Ticket>> getBookedTickets(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(ticketService.findByUserId(userId));
    }

    @PutMapping("/{userId}/")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        else {
            User user = userService.findById(userId);
            if (userDTO.getFirstName() != null)
                user.setFirstName(userDTO.getFirstName());
            if (userDTO.getLastName() != null)
                user.setLastName(userDTO.getLastName());
            if (userDTO.getTelephoneNumber() != null)
                user.setTelephoneNumber(userDTO.getTelephoneNumber());
            if (userDTO.getEnabled() != null)
                user.setEnabled(userDTO.getEnabled());
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @DeleteMapping("/{userId}/")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId){
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{userId}/enable")
    public ResponseEntity enableUser(@PathVariable("userId") Long userId){
        userService.enableUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Enabled " + userId);
    }

    @PostMapping("/{userId}/disable")
    public ResponseEntity disableUser(@PathVariable("userId") Long userId){
        userService.disableUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Disabled " + userId);
    }

}
