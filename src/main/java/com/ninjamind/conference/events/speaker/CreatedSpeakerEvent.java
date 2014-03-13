package com.ninjamind.conference.events.speaker;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class CreatedSpeakerEvent extends AbstractSpeakerEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param speaker
     */
    public CreatedSpeakerEvent(SpeakerDetail speaker) {
        super(speaker);
    }

    /**
     * @param validEntity
     * @param speaker
     */
    public CreatedSpeakerEvent(boolean validEntity, SpeakerDetail speaker) {
        super(speaker);
        this.validEntity=validEntity;
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
