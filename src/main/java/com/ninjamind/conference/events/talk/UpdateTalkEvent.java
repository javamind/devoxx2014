package com.ninjamind.conference.events.talk;

/**
 * AbstractEvent retourne lors de la recherche de la liste des conferences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class UpdateTalkEvent extends CreateTalkEvent {

    public UpdateTalkEvent(TalkDetail talk) {
        super(talk);
    }
}
