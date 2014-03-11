package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.events.AbstractEvent;

import java.util.UUID;

/**
 * Evenement de base associe à une {@link com.ninjamind.conference.domain.Conference}
 *
 * @author EHRET_G
 */
public abstract class AbstractConferenceEvent extends AbstractEvent {
    /**
     * Enreg retourne
     */
    protected ConferenceDetail conference;
    /**
     * Entity found
     */
    protected boolean entityFound = true;

    /**
     *
     * @param uuid
     * @param entityFound
     * @param conference
     */
    public AbstractConferenceEvent(UUID uuid, boolean entityFound, ConferenceDetail conference) {
        this(uuid, conference);
        this.entityFound = entityFound;
    }

    /**
     *
     * @param uuid
     * @param conference
     */
    public AbstractConferenceEvent(UUID uuid, ConferenceDetail conference) {
        this.key = uuid;
        this.conference = conference;
    }

    /**
     *
     * @return
     */
    public boolean isEntityFound() {
        return entityFound;
    }

    /**
     *
     * @return
     */
    public ConferenceDetail getConference() {
        return conference;
    }
}
