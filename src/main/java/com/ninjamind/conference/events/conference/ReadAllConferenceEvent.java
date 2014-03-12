package com.ninjamind.conference.events.conference;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.AbstractEvent;

import java.util.List;

/**
 * AbstractEvent retourne lors de la recherche de la liste des conferences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadAllConferenceEvent extends AbstractEvent {

    protected List<ConferenceDetail> conferences;

    public ReadAllConferenceEvent() {
    }

    public ReadAllConferenceEvent(List<ConferenceDetail> conferences) {
        this.conferences = conferences;
    }

    /**
     * Initie l'objet en convertissant les {@link com.ninjamind.conference.domain.Conference}
     * en {@link com.ninjamind.conference.events.conference.ConferenceDetail}
     * @param conferences
     */
    public void populateFromConferenceList(List<Conference> conferences) {
        //On convertit la liste reçu en DTO attendu par la couche REST
        this.conferences =
                Lists.transform(conferences, new Function<Conference, ConferenceDetail>() {
                    @Override
                    public ConferenceDetail apply(Conference conference) {
                        return new ConferenceDetail(conference);
                    }
                });
    }

    public List<ConferenceDetail> getConferences() {
        return conferences;
    }

}
