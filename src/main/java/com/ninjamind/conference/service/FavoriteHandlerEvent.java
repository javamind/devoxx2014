package com.ninjamind.conference.service;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation de {@link com.ninjamind.conference.service.FavoriteService}
 *
 * @author EHRET_G
 * @author Agnes
 */
public class FavoriteHandlerEvent implements FavoriteService {

    private static Logger LOG = LoggerFactory.make();

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Override
    public Conference getTheMoreSelectiveConference() throws Exception {
        List<Conference> conferences = conferenceRepository.findAll();
        //On calcule les indicateurs que l'on va retourner dans une liste
        Collection<Conference> results = FluentIterable
                .from(conferences)
                .filter(new Predicate<Conference>() {
                    @Override
                    public boolean apply(Conference conference) {
                        //Si une des donnees est vide conf est hors concours
                        return (conference.getProposalsRatio() != null);
                    }
                })
                .toSortedList(new Comparator<Conference>() {
                    @Override
                    public int compare(Conference c1, Conference c2) {
                        return Double.compare(c1.getProposalsRatio(),c2.getProposalsRatio());
                    }
                });

        if (results == null || results.isEmpty()) {
            throw new Exception("Aucune conference evaluée");
        }
        return results.iterator().next();
    }
}
