package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.Event;

import java.util.List;

/**
 * Event retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadAllConferenceEvent implements Event{

    private List<Conference> conferences;

    public List<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }
}
