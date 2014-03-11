package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.events.AbstractEvent;

import java.util.UUID;

/**
 * Evenement de base associe � une {@link com.ninjamind.conference.domain.Conference}
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
     * @param entityFound
     * @param conference
     */
    public AbstractConferenceEvent(boolean entityFound, ConferenceDetail conference) {
        this(conference);
        this.entityFound = entityFound;
    }

    /**
     * @param conference
     */
    public AbstractConferenceEvent(ConferenceDetail conference) {
        super();
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
