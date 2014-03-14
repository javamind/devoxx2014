package com.ninjamind.conference.events.speaker;

import com.ninjamind.conference.events.conference.ConferenceDetail;
import com.ninjamind.conference.events.conference.CreateConferenceEvent;

/**
 * AbstractEvent retourne lors de la recherche de la liste des conferences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class UpdateSpeakerEvent extends CreateSpeakerEvent {

    public UpdateSpeakerEvent(SpeakerDetail speaker) {
        super(speaker);
    }
}
