package com.ninjamind.conference.events.speaker;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.conference.AbstractConferenceEvent;
import com.ninjamind.conference.events.conference.ConferenceDetail;

/**
 * AbstractEvent retourne lors de la recherche de la liste des speakers
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class UpdatedSpeakerEvent extends AbstractSpeakerEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param entityFound
     * @param speaker
     */
    public UpdatedSpeakerEvent(boolean entityFound, SpeakerDetail speaker) {
        super(entityFound, speaker);
    }

    /**
     * @param validEntity
     * @param entityFound
     * @param speaker
     */
    public UpdatedSpeakerEvent(boolean validEntity, boolean entityFound, SpeakerDetail speaker) {
        super(entityFound, speaker);
        this.validEntity=validEntity;
    }

    /**
     *
     * @param speaker
     * @param speaker
     */
    public UpdatedSpeakerEvent(Speaker speaker) {
        this(speaker != null, speaker != null ? new SpeakerDetail(speaker) : null);
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
