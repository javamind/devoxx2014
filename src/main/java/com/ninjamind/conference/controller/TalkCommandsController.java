package com.ninjamind.conference.controller;

import com.ninjamind.conference.events.speaker.*;
import com.ninjamind.conference.events.talk.*;
import com.ninjamind.conference.service.SpeakerService;
import com.ninjamind.conference.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/talks")
public class TalkCommandsController {
    /**
     * Service associe permettant de gérer les {@link com.ninjamind.conference.domain.Talk}
     */
    @Autowired
    private TalkService talkService;

    /**
     * Essaye de creer un nouvel enregistrement
     * <code>
     * uri : /talks
     * status :
     * <ul>
     *  <li>201 - {@link org.springframework.http.HttpStatus#CREATED}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param talk
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TalkDetail> create(@RequestBody  TalkDetail talk) {
        CreatedTalkEvent createdTalkEvent =  talkService.createTalk(new CreateTalkEvent(talk));

        if(!createdTalkEvent.isValidEntity()){
            return new ResponseEntity<TalkDetail>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<TalkDetail>(createdTalkEvent.getTalk(), HttpStatus.CREATED);
    }

    /**
     * Essaye de supprimer un enregistrement
     * <code>
     * uri : /talks/{id}
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
    public ResponseEntity<TalkDetail> delete(@PathVariable String id) {
        DeletedTalkEvent deletedTalkEvent =  talkService.deleteTalk(new DeleteTalkEvent(id));

        if(!deletedTalkEvent.isEntityFound()){
            return new ResponseEntity<TalkDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TalkDetail>(deletedTalkEvent.getTalk(), HttpStatus.OK);
    }

    /**
     * Essaye de modifier un enregistrement
     * <code>
     * uri : /talks
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param talk
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<TalkDetail> update(@RequestBody TalkDetail talk) {
        UpdatedTalkEvent updatedTalkEvent =  talkService.updateTalk(new UpdateTalkEvent(talk));

        if(!updatedTalkEvent.isEntityFound()){
            return new ResponseEntity<TalkDetail>(HttpStatus.NOT_FOUND);
        }
        if(!updatedTalkEvent.isValidEntity()){
            return new ResponseEntity<TalkDetail>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<TalkDetail>(updatedTalkEvent.getTalk(), HttpStatus.OK);
    }

}
