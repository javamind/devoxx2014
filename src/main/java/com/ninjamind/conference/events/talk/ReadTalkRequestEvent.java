package com.ninjamind.conference.events.talk;

import com.ninjamind.conference.events.AbstractEvent;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class ReadTalkRequestEvent extends AbstractEvent {

    /**
     * Id du talk
     */
    protected String id;

    public ReadTalkRequestEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
