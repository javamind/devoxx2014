package com.ninjamind.conference.events.speaker;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.conference.AbstractConferenceEvent;
import com.ninjamind.conference.events.conference.ConferenceDetail;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class ReadSpeakerEvent extends AbstractSpeakerEvent {
    /**
     * @param entityFound
     * @param speaker
     */
    public ReadSpeakerEvent(boolean entityFound, SpeakerDetail speaker) {
        super(entityFound, speaker);
    }

    /**
     * @param speaker
     */
    public ReadSpeakerEvent(Speaker speaker) {
        this(speaker != null, speaker != null ? new SpeakerDetail(speaker) : null);
    }
}
