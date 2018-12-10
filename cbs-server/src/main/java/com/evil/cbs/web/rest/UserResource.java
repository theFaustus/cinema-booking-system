package com.evil.cbs.web.rest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserResource {

    private final Logger LOGGER = Logger.getLogger(this.getClass());


    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    public List<String> list() {
        LOGGER.info("Accessed.");
        return new ArrayList<>();
    }

}
