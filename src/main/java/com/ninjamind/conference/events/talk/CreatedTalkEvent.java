package com.ninjamind.conference.events.talk;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class CreatedTalkEvent extends AbstractTalkEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param talk
     */
    public CreatedTalkEvent(TalkDetail talk) {
        super(talk);
    }

    /**
     * @param validEntity
     * @param talk
     */
    public CreatedTalkEvent(boolean validEntity, TalkDetail talk) {
        super(talk);
        this.validEntity=validEntity;
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
