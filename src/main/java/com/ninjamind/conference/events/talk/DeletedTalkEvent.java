package com.ninjamind.conference.events.talk;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class DeletedTalkEvent extends AbstractTalkEvent {
    /**
     *
     * @param entityFound
     * @param talk
     */
    public DeletedTalkEvent(boolean entityFound, TalkDetail talk) {
        super(entityFound, talk);
    }
}
