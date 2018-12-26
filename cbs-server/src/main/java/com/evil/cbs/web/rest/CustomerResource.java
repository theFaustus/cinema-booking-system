package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Customer;
import com.evil.cbs.service.CustomerService;
import com.evil.cbs.web.form.RegisterCustomerFormBean;
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
@RequestMapping("/v1/api/customer")
public class CustomerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/booked-tickets-list", method = RequestMethod.GET)
    public List<String> getBookedTickets() {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerCustomer(@Valid @RequestBody RegisterCustomerFormBean registerCustomerFormBean, BindingResult bindingResult){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        else {
            try {
                Customer c = customerService.saveCustomer(registerCustomerFormBean);
                return  ResponseEntity.status(HttpStatus.CREATED).body(c);
            } catch (Exception e){
                LOGGER.error("Customer not registered!", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @RequestMapping(value = "/{customerEmail}/enable", method = RequestMethod.POST)
    public ResponseEntity enableCustomer(@PathVariable String customerEmail){
        try {
            customerService.enableCustomer(customerEmail);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @RequestMapping(value = "/{customerEmail}/disable", method = RequestMethod.POST)
    public ResponseEntity disableCustomer(@PathVariable String customerEmail){
        try {
            customerService.disableCustomer(customerEmail);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Customer> getAllCustomers(){
        return customerService.findAll();
    }

}
