package com.ninjamind.conference.events.talk;

import com.ninjamind.conference.events.AbstractEvent;

/**
 * Evenement de base associe a une {@link com.ninjamind.conference.domain.Talk}
 *
 * @author EHRET_G
 */
public abstract class AbstractTalkEvent extends AbstractEvent {
    /**
     * Enreg retourne
     */
    protected TalkDetail talk;
    /**
     * Entity found
     */
    protected boolean entityFound = true;

    /**
     *
     * @param entityFound
     * @param talk
     */
    public AbstractTalkEvent(boolean entityFound, TalkDetail talk) {
        this(talk);
        this.entityFound = entityFound;
    }

    /**
     * @param talk
     */
    public AbstractTalkEvent(TalkDetail talk) {
        super();
        this.talk = talk;
    }

    /**
     *
     * @return
     */
    public boolean isEntityFound() {
        return entityFound;
    }

    /**
     *
     * @return
     */
    public TalkDetail getTalk() {
        return talk;
    }
}
