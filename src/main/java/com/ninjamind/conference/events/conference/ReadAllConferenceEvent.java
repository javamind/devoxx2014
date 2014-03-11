package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.events.AbstractEvent;

import java.util.List;

/**
 * AbstractEvent retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadAllConferenceEvent extends AbstractEvent {

    protected List<ConferenceDetail> conferences;

    public List<ConferenceDetail> getConferences() {
        return conferences;
    }

}
