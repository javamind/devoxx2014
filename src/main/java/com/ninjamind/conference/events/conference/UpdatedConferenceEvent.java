package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourn� lors de la recherche de la liste des conf�rences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class UpdatedConferenceEvent extends AbstractConferenceEvent {
    /**
     *
     * @param entityFound
     * @param conference
     */
    public UpdatedConferenceEvent(boolean entityFound, ConferenceDetail conference) {
        super(entityFound, conference);
    }
}
