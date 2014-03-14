package com.ninjamind.conference.events.speaker;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class DeletedSpeakerEvent extends AbstractSpeakerEvent {
    /**
     *
     * @param entityFound
     * @param speaker
     */
    public DeletedSpeakerEvent(boolean entityFound, SpeakerDetail speaker) {
        super(entityFound, speaker);
    }
}
