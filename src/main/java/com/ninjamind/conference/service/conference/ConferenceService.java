package com.ninjamind.conference.service.conference;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;

import java.util.List;

/**
 * Service lié aux {@link com.ninjamind.conference.domain.Conference}
 */
public interface ConferenceService {

    /**
     * Permet de retourner la liste des conferences
     * @return
     */
    List<Conference> getAllConference();

     /**
     * Permet de retourner une conference
     * @param params
     * @return
     */
     Conference getConference(Conference params);

    /**
     * Creation d'une conference
     * @param event
     * @return
     */
    CreatedEvent<Conference> createConference(Conference event);

    /**
     * Modification d'une conference
     * @param event
     * @return
     */
    UpdatedEvent<Conference> updateConference(Conference event);

    /**
     * Suppression d'une conference
     * @param event
     * @return
     */
    DeletedEvent<Conference> deleteConference(Conference event);

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
     * @return
     */
    Conference getCoolestConference();

}
