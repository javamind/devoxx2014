package com.ninjamind.conference.events.talk;

import com.ninjamind.conference.domain.Talk;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class ReadTalkEvent extends AbstractTalkEvent {
    /**
     * @param entityFound
     * @param talk
     */
    public ReadTalkEvent(boolean entityFound, TalkDetail talk) {
        super(entityFound, talk);
    }

    /**
     * @param talk
     */
    public ReadTalkEvent(Talk talk) {
        this(talk != null, talk != null ? new TalkDetail(talk) : null);
    }
}
