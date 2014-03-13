package com.ninjamind.conference.events.speaker;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class CreateSpeakerEvent extends AbstractSpeakerEvent {

    public CreateSpeakerEvent(SpeakerDetail speaker) {
        super(speaker);
    }
}
