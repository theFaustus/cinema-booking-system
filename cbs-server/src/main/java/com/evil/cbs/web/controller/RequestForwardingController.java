package com.evil.cbs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestForwardingController {

    @RequestMapping(value = "/**/{path:[^.]*}")
    public String redirect() {
        // Forward to home page so that route is preserved.
        return "forward:/";
    }

}