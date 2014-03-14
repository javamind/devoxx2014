package com.ninjamind.conference.events.talk;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class CreateTalkEvent extends AbstractTalkEvent {

    public CreateTalkEvent(TalkDetail talk) {
        super(talk);
    }
}
