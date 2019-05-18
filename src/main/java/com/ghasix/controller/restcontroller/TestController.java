package com.ghasix.controller.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("main-frame");
    }

    @GetMapping("/record")
    public ModelAndView record() {
        return new ModelAndView("commutes-record");
    }
    
    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("commutes-list");
    }

    @GetMapping("/statistics")
    public ModelAndView statistics() {
        return new ModelAndView("commutes-statistics");
    }
}