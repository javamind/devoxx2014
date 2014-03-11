package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class CreateConferenceEvent extends AbstractConferenceEvent {

    public CreateConferenceEvent(ConferenceDetail conference) {
        super(conference);
    }
}
