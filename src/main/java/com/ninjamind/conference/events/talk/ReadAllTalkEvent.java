package com.ninjamind.conference.events.talk;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.AbstractEvent;

import java.util.List;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class ReadAllTalkEvent extends AbstractEvent {

    protected List<TalkDetail> talks;

    public ReadAllTalkEvent() {
    }

    public ReadAllTalkEvent(List<TalkDetail> talks) {
        this.talks = talks;
    }

    /**
     * Initie l'objet en convertissant les {@link com.ninjamind.conference.domain.Talk}
     * en {@link TalkDetail}
     * @param talks
     */
    public void populateFromTalkList(List<Talk> talks) {
        //On convertit la liste reçu en DTO attendu par la couche REST
        this.talks =
                Lists.transform(talks, new Function<Talk, TalkDetail>() {
                    @Override
                    public TalkDetail apply(Talk talk) {
                        return new TalkDetail(talk);
                    }
                });
    }

    public List<TalkDetail> getTalks() {
        return talks;
    }

}
