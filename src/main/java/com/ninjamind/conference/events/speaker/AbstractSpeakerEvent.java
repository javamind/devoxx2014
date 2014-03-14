package com.ninjamind.conference.events.speaker;

import com.ninjamind.conference.events.AbstractEvent;

/**
 * Evenement de base associe a une {@link com.ninjamind.conference.domain.Speaker}
 *
 * @author EHRET_G
 */
public abstract class AbstractSpeakerEvent extends AbstractEvent {
    /**
     * Enreg retourne
     */
    protected SpeakerDetail speaker;
    /**
     * Entity found
     */
    protected boolean entityFound = true;

    /**
     *
     * @param entityFound
     * @param speaker
     */
    public AbstractSpeakerEvent(boolean entityFound, SpeakerDetail speaker) {
        this(speaker);
        this.entityFound = entityFound;
    }

    /**
     * @param speaker
     */
    public AbstractSpeakerEvent(SpeakerDetail speaker) {
        super();
        this.speaker = speaker;
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
    public SpeakerDetail getSpeaker() {
        return speaker;
    }
}
