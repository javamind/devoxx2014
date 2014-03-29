package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.*;

/**
 * Service permettant de détermine les favoris des utilisateurs
 */
public interface FavoriteService {

    /**
     * Permet la conference que les personnes trouvent la plus cool. Le calcul se base sur
     * des métriques précises
     * <ul>
     *     <li>Interet des speakers : Rapport entre le nombre de slots de la conference par rapport au nombre de soumiision
     *     des speakers</li>
     *     <li>Interet des internautes : nombre d'abonne twitter par rapport au nombre de participants</li>
     *     <li>Interet des participants : temps moyen pour qu'une place soit vendue</li>
     * </ul>
     * Ces différents indicateurs comptent autant les uns que les autres. Par contre si une des valeurs n'est
     * pas renseignee la Conference ne participe pas au calcul
     * @param event
     * @return
     */
    ReadConferenceEvent getCoolestConference(ReadCoolestConferenceRequestEvent event);

    Conference getTheMoreSelectiveConference();

}
