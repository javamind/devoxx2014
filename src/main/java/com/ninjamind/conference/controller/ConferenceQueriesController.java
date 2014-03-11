package com.ninjamind.conference.controller;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.ConferenceDetail;
import com.ninjamind.conference.events.conference.ReadAllConferenceRequestEvent;
import com.ninjamind.conference.events.conference.ReadConferenceEvent;
import com.ninjamind.conference.events.conference.ReadConferenceRequestEvent;
import com.ninjamind.conference.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/conferences")
public class ConferenceQueriesController {
    /**
     * Service associe permettant de gérer les {@link com.ninjamind.conference.domain.Conference}
     */
    @Autowired
    private ConferenceService conferenceService;

    /**
     * Retourne la liste complete
     * <code>
     * uri : /conferences
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     * </ul>
     * </code>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ConferenceDetail> getAll() {
        return conferenceService.getAllConference(new ReadAllConferenceRequestEvent()).getConferences();
    }

    /**
     * Retourne l'enregistrement suivant id
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
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ConferenceDetail> getById(@PathVariable String id) {
        ReadConferenceEvent readConferenceEvent = conferenceService.getConference(new ReadConferenceRequestEvent(id));

        if(!readConferenceEvent.isEntityFound()){
            return new ResponseEntity<ConferenceDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ConferenceDetail>(readConferenceEvent.getConference(), HttpStatus.OK);
    }

    /**
     *
     * @param conferenceService
     */
    public void setConferenceService(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }
}
