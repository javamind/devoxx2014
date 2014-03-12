package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class CreatedConferenceEvent extends AbstractConferenceEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param conference
     */
    public CreatedConferenceEvent(ConferenceDetail conference) {
        super(conference);
    }

    /**
     * @param validEntity
     * @param conference
     */
    public CreatedConferenceEvent(boolean validEntity, ConferenceDetail conference) {
        super(conference);
        this.validEntity=validEntity;
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
