package com.ninjamind.conference.controller;

import com.ninjamind.conference.events.talk.ReadAllTalkRequestEvent;
import com.ninjamind.conference.events.talk.ReadTalkEvent;
import com.ninjamind.conference.events.talk.ReadTalkRequestEvent;
import com.ninjamind.conference.events.talk.TalkDetail;
import com.ninjamind.conference.service.TalkService;
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
@RequestMapping("/talks")
public class TalkQueriesController {
    /**
     * Service associe permettant de gérer les {@link com.ninjamind.conference.domain.Talk}
     */
    @Autowired
    private TalkService talkService;

    /**
     * Retourne la liste complete
     * <code>
     * uri : /talks
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
    public List<TalkDetail> getAll() {
        return talkService.getAllTalk(new ReadAllTalkRequestEvent()).getTalks();
    }

    /**
     * Retourne l'enregistrement suivant id
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
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public ResponseEntity<TalkDetail> getById(@PathVariable String id) {
        ReadTalkEvent readTalkEvent = talkService.getTalk(new ReadTalkRequestEvent(id));

        if(!readTalkEvent.isEntityFound()){
            return new ResponseEntity<TalkDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TalkDetail>(readTalkEvent.getTalk(), HttpStatus.OK);
    }

}
