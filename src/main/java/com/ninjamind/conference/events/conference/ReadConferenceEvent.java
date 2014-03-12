package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.domain.Conference;

import java.util.UUID;

/**
 * AbstractEvent retourn� lors de la recherche de la liste des conf�rences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadConferenceEvent extends AbstractConferenceEvent {
    /**
     * @param entityFound
     * @param conference
     */
    public ReadConferenceEvent(boolean entityFound, ConferenceDetail conference) {
        super(entityFound, conference);
    }

    /**
     * @param conference
     */
    public ReadConferenceEvent(Conference conference) {
        this(true, conference != null ? new ConferenceDetail(conference) : null);
    }
}
