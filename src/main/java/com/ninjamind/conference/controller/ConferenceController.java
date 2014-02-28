package com.ninjamind.conference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/")
public class ConferenceController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentMenu() {
        return "test";
    }
}
