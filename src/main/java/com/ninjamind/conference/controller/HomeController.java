package com.ninjamind.conference.controller;

import com.ninjamind.conference.events.conference.*;
import com.ninjamind.conference.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * TODO change with an angular interface
 * @author ehret_g
 */
@Controller
@RequestMapping("/")
public class HomeController {
    /**
     *
     * <code>
     * uri : /
     * status : 200 - {@link org.springframework.http.HttpStatus#OK}
     * </code>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> home() {
        return new ResponseEntity<String>("Page d'accueil présentation Devox2014", HttpStatus.OK);
    }
}
