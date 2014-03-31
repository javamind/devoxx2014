package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.*;

/**
 * Service permettant de détermine les favoris des utilisateurs
 */
public interface FavoriteService {

    /**
     * Recupération de la conference la plus sélective parmis toutes les conférences
     * (la plus difficile d'accès en tant que speaker : nb de talk soumis par rapport
     * au nombre retenu)
     * @return
     */
    Conference getTheMoreSelectiveConference();

}
