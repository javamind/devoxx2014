package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.events.Event;

/**
 * Event envoyé pour rechercher la liste des conférences. Contient l'ensemble des critères de
 * recherche
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ReadConferenceRequestEvent implements Event{

    /**
     * Id de la conference
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
