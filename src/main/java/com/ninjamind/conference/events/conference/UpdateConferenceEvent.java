package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class UpdateConferenceEvent extends AbstractConferenceEvent {

    public UpdateConferenceEvent(ConferenceDetail conference) {
        super(UUID.randomUUID(), conference);
    }
}
