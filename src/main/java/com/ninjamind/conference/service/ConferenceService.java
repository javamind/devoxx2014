package com.ninjamind.conference.service;

import com.ninjamind.conference.events.conference.*;

/**
 * Service lié aux {@link com.ninjamind.conference.domain.Conference}
 */
public interface ConferenceService {

    /**
     * Permet de retourner la liste des conferences
     * @param event
     * @return
     */
    ReadAllConferenceEvent getAllConference(ReadAllConferenceRequestEvent event);

     /**
     * Permet de retourner une conference
     * @param event
     * @return
     */
    ReadConferenceEvent getConference(ReadConferenceRequestEvent event);

    /**
     * Creation d'une conference
     * @param event
     * @return
     */
    CreatedConferenceEvent createConference(CreateConferenceEvent event);

    /**
     * Modification d'une conference
     * @param event
     * @return
     */
    UpdatedConferenceEvent updateConference(UpdateConferenceEvent event);

    /**
     * Suppression d'une conference
     * @param event
     * @return
     */
    DeletedConferenceEvent deleteConference(DeleteConferenceEvent event);


}
