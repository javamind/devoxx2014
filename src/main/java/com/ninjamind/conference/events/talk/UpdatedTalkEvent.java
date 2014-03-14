package com.ninjamind.conference.events.talk;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.conference.AbstractConferenceEvent;
import com.ninjamind.conference.events.conference.ConferenceDetail;

/**
 * AbstractEvent retourne lors de la recherche de la liste des talks
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class UpdatedTalkEvent extends AbstractTalkEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param entityFound
     * @param talk
     */
    public UpdatedTalkEvent(boolean entityFound, TalkDetail talk) {
        super(entityFound, talk);
    }

    /**
     * @param validEntity
     * @param entityFound
     * @param talk
     */
    public UpdatedTalkEvent(boolean validEntity, boolean entityFound, TalkDetail talk) {
        super(entityFound, talk);
        this.validEntity=validEntity;
    }

    /**
     *
     * @param talk
     * @param talk
     */
    public UpdatedTalkEvent(Talk talk) {
        this(talk != null, talk != null ? new TalkDetail(talk) : null);
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
