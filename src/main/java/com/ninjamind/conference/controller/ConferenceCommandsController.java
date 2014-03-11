package com.ninjamind.conference.controller;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/conferences")
public class ConferenceCommandsController {
    /**
     * Service associe permettant de gérer les {@link com.ninjamind.conference.domain.Conference}
     */
    private ConferenceService conferenceService;

    /**
     * Essaye de creer un nouvel enregistrement
     * <code>
     * uri : /conferences
     * status :
     * <ul>
     *  <li>201 - {@link org.springframework.http.HttpStatus#CREATED}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param conference
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Conference> create(@RequestBody Conference conference) {
        return new ResponseEntity<Conference>(new Conference("test", null, null), HttpStatus.CREATED);
    }

    /**
     * Essaye de supprimer un enregistrement
     * <code>
     * uri : /conferences/{id}
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     * </ul>
     * </code>
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Conference> delete(@PathVariable String id) {
        return new ResponseEntity<Conference>(new Conference("test", null, null), HttpStatus.OK);
    }

    /**
     * Essaye de modifier un enregistrement
     * <code>
     * uri : /conferences
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Conference> update(@RequestBody Conference id) {
        return new ResponseEntity<Conference>(new Conference("test", null, null), HttpStatus.OK);
    }

    /**
     *
     * @param conferenceService
     */
    public void setConferenceService(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }
}
