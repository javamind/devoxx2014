package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourn� lors de la recherche de la liste des conf�rences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class CreatedConferenceEvent extends AbstractConferenceEvent {
    /**
     *
     * @param conference
     */
    public CreatedConferenceEvent(ConferenceDetail conference) {
        super(conference);
    }
}
