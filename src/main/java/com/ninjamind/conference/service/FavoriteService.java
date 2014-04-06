package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;

import java.util.List;

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
    Conference getTheMoreSelectiveConference() throws Exception;

    /**
     * Recupération des conference les plus hypes parmis toutes les conférences
     * Basé sur le Nb de Followers Twitter (>800)
     * @return les conferences classees par ordre decroissant du nb de followers Twitter
     */
    List<Conference> getTheHypestConfs() throws Exception;

}
