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
     * @param uuid
     * @param conference
     */
    public CreatedConferenceEvent(UUID uuid, ConferenceDetail conference) {
        super(uuid, conference);
    }
}
