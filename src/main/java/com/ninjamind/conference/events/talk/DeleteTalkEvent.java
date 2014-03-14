package com.ninjamind.conference.events.talk;

import com.ninjamind.conference.events.AbstractEvent;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class DeleteTalkEvent extends AbstractEvent {

    /**
     * Id de la conference
     */
    protected String id;

    public DeleteTalkEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
