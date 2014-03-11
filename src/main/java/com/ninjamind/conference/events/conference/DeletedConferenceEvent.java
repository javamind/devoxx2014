package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class DeletedConferenceEvent extends AbstractConferenceEvent {
    /**
     *
     * @param entityFound
     * @param conference
     */
    public DeletedConferenceEvent(boolean entityFound, ConferenceDetail conference) {
        super(entityFound, conference);
    }
}
