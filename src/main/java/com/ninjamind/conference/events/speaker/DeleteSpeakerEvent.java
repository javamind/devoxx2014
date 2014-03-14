package com.ninjamind.conference.events.speaker;

import com.ninjamind.conference.events.AbstractEvent;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class DeleteSpeakerEvent extends AbstractEvent {

    /**
     * Id de la conference
     */
    protected String id;

    public DeleteSpeakerEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
