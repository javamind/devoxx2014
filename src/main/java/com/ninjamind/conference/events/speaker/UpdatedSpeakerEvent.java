package com.ninjamind.conference.events.speaker;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.AbstractConferenceEvent;
import com.ninjamind.conference.events.conference.ConferenceDetail;

/**
 * AbstractEvent retourn� lors de la recherche de la liste des conf�rences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class UpdatedSpeakerEvent extends AbstractConferenceEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param entityFound
     * @param conference
     */
    public UpdatedSpeakerEvent(boolean entityFound, ConferenceDetail conference) {
        super(entityFound, conference);
    }

    /**
     * @param validEntity
     * @param entityFound
     * @param conference
     */
    public UpdatedSpeakerEvent(boolean validEntity, boolean entityFound, ConferenceDetail conference) {
        super(entityFound, conference);
        this.validEntity=validEntity;
    }

    /**
     *
     * @param conference
     * @param conference
     */
    public UpdatedSpeakerEvent(Conference conference) {
        this(conference != null, conference != null ? new ConferenceDetail(conference) : null);
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
