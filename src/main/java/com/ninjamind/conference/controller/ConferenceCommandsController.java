package com.ninjamind.conference.controller;

import com.ninjamind.conference.events.conference.*;
import com.ninjamind.conference.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/conferences")
public class ConferenceCommandsController {
    /**
     * Service associe permettant de gérer les {@link com.ninjamind.conference.domain.Conference}
     */
    @Autowired
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
    public ResponseEntity<ConferenceDetail> create(@RequestBody  ConferenceDetail conference) {
        CreatedConferenceEvent createdConferenceEvent =  conferenceService.createConference(new CreateConferenceEvent(conference));

        if(!createdConferenceEvent.isValidEntity()){
            return new ResponseEntity<ConferenceDetail>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<ConferenceDetail>(createdConferenceEvent.getConference(), HttpStatus.CREATED);
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
    @ResponseBody
    public ResponseEntity<ConferenceDetail> delete(@PathVariable String id) {
        DeletedConferenceEvent deletedConferenceEvent =  conferenceService.deleteConference(new DeleteConferenceEvent(id));

        if(!deletedConferenceEvent.isEntityFound()){
            return new ResponseEntity<ConferenceDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ConferenceDetail>(deletedConferenceEvent.getConference(), HttpStatus.OK);
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
     * @param conference
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<ConferenceDetail> update(@RequestBody  ConferenceDetail conference) {
        UpdatedConferenceEvent updatedConferenceEvent =  conferenceService.updateConference(new UpdateConferenceEvent(conference));

        if(!updatedConferenceEvent.isEntityFound()){
            return new ResponseEntity<ConferenceDetail>(HttpStatus.NOT_FOUND);
        }
        if(!updatedConferenceEvent.isValidEntity()){
            return new ResponseEntity<ConferenceDetail>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<ConferenceDetail>(updatedConferenceEvent.getConference(), HttpStatus.OK);
    }

}
