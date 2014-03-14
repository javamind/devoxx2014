package com.ninjamind.conference.events.speaker;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.AbstractEvent;

import java.util.List;

/**
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class ReadAllSpeakerEvent extends AbstractEvent {

    protected List<SpeakerDetail> speakers;

    public ReadAllSpeakerEvent() {
    }

    public ReadAllSpeakerEvent(List<SpeakerDetail> speakers) {
        this.speakers = speakers;
    }

    /**
     * Initie l'objet en convertissant les {@link com.ninjamind.conference.domain.Speaker}
     * en {@link com.ninjamind.conference.events.speaker.SpeakerDetail}
     * @param speakers
     */
    public void populateFromSpeakerList(List<Speaker> speakers) {
        //On convertit la liste reçu en DTO attendu par la couche REST
        this.speakers =
                Lists.transform(speakers, new Function<Speaker, SpeakerDetail>() {
                    @Override
                    public SpeakerDetail apply(Speaker speaker) {
                        return new SpeakerDetail(speaker);
                    }
                });
    }

    public List<SpeakerDetail> getSpeakers() {
        return speakers;
    }

}
