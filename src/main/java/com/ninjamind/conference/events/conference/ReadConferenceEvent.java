package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourn� lors de la recherche de la liste des conf�rences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadConferenceEvent extends AbstractConferenceEvent {
    /**
     *
     * @param uuid
     * @param entityFound
     * @param conference
     */
    public ReadConferenceEvent(UUID uuid, boolean entityFound, ConferenceDetail conference) {
        super(uuid, entityFound, conference);
    }
}
