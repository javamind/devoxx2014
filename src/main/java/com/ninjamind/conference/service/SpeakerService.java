package com.ninjamind.conference.service;

import com.ninjamind.conference.events.speaker.*;

/**
 * Service lié aux {@link com.ninjamind.conference.domain.Speaker}
 */
public interface SpeakerService {

    /**
     * Permet de retourner la liste des speakers
     * @param event
     * @return
     */
    ReadAllSpeakerEvent getAllSpeaker(ReadAllSpeakerRequestEvent event);

    /**
     * Permet de retourner un speaker
     * @param event
     * @return
     */
    ReadSpeakerEvent getSpeaker(ReadSpeakerRequestEvent event);

    /**
     * Creation d'un speaker
     * @param event
     * @return
     */
    CreatedSpeakerEvent createSpeaker(CreateSpeakerEvent event);

    /**
     * Modification d'un speaker
     * @param event
     * @return
     */
    UpdatedSpeakerEvent updateSpeaker(UpdateSpeakerEvent event);

    /**
     * Suppression d'un speaker
     * @param event
     * @return
     */
    DeletedSpeakerEvent deleteSpeaker(DeleteSpeakerEvent event);
}
