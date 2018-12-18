package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Customer;
import com.evil.cbs.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserResource {

    Logger logger = LoggerFactory.getLogger(this.getClass());



    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    public List<String> list() {
        return new ArrayList<>();
    }

}
