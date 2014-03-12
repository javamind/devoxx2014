package com.ninjamind.conference.events.conference;

import java.util.UUID;

/**
 * AbstractEvent retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class UpdatedConferenceEvent extends AbstractConferenceEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param entityFound
     * @param conference
     */
    public UpdatedConferenceEvent(boolean entityFound, ConferenceDetail conference) {
        super(entityFound, conference);
    }

    /**
     * @param validEntity
     * @param entityFound
     * @param conference
     */
    public UpdatedConferenceEvent(boolean validEntity, boolean entityFound, ConferenceDetail conference) {
        super(entityFound, conference);
        this.validEntity=validEntity;
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
