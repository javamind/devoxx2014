package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.events.AbstractEvent;

/**
 * AbstractEvent envoyé pour rechercher la liste des conférences. Contient l'ensemble des critères de
 * recherche
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadConferenceRequestEvent extends AbstractEvent {

    /**
     * Id de la conference
     */
    protected String id;

    public ReadConferenceRequestEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
