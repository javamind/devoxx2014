package com.ninjamind.conference.events.speaker;

import com.ninjamind.conference.events.AbstractEvent;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class ReadSpeakerRequestEvent extends AbstractEvent {

    /**
     * Id du speaker
     */
    protected String id;

    public ReadSpeakerRequestEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
