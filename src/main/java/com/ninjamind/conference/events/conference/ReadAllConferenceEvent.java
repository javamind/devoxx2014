package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.events.AbstractEvent;

import java.util.List;

/**
 * AbstractEvent retourn� lors de la recherche de la liste des conf�rences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadAllConferenceEvent extends AbstractEvent {

    protected List<ConferenceDetail> conferences;

    public ReadAllConferenceEvent(List<ConferenceDetail> conferences) {
        this.conferences = conferences;
    }

    public List<ConferenceDetail> getConferences() {
        return conferences;
    }

}
