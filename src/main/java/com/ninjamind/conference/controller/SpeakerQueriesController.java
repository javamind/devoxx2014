package com.ninjamind.conference.controller;

import com.ninjamind.conference.events.speaker.ReadAllSpeakerRequestEvent;
import com.ninjamind.conference.events.speaker.ReadSpeakerEvent;
import com.ninjamind.conference.events.speaker.ReadSpeakerRequestEvent;
import com.ninjamind.conference.events.speaker.SpeakerDetail;
import com.ninjamind.conference.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/speakers")
public class SpeakerQueriesController {
    /**
     * Service associe permettant de gérer les {@link com.ninjamind.conference.domain.Speaker}
     */
    @Autowired
    private SpeakerService speakerService;

    /**
     * Retourne la liste complete
     * <code>
     * uri : /speakers
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
    public List<SpeakerDetail> getAll() {
        return speakerService.getAllSpeaker(new ReadAllSpeakerRequestEvent()).getSpeakers();
    }

    /**
     * Retourne l'enregistrement suivant id
     * <code>
     * uri : /speakers/{id}
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
    @ResponseBody
    public ResponseEntity<SpeakerDetail> getById(@PathVariable String id) {
        ReadSpeakerEvent readSpeakerEvent = speakerService.getSpeaker(new ReadSpeakerRequestEvent(id));

        if(!readSpeakerEvent.isEntityFound()){
            return new ResponseEntity<SpeakerDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SpeakerDetail>(readSpeakerEvent.getSpeaker(), HttpStatus.OK);
    }

}
