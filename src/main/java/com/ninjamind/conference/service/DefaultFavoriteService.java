package com.ninjamind.conference.service;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Implementation de {@link com.ninjamind.conference.service.FavoriteService}
 *
 * @author EHRET_G
 * @author Agnes
 */
@Service
public class DefaultFavoriteService implements FavoriteService {

    @Autowired
    private ConferenceRepository conferenceRepository;


    @Override
    public List<Conference> getTheHypestConfs() throws Exception {
        List<Conference> conferences = conferenceRepository.findAll();
        //On calcule les indicateurs que l'on va retourner dans une liste

        List<Conference> results = FluentIterable
                .from(conferences)
                .filter(new Predicate<Conference>() {
                    @Override
                    public boolean apply(Conference conference) {
                        return conference.getProposalsRatio() != null;
                    }
                })
                .toSortedList(new Comparator<Conference>() {
                    @Override
                    // les conferences sont classees par ordre decroissant du nb de followers Twitter
                    public int compare(Conference c1, Conference c2) {
                        return Double.compare(c1.getProposalsRatio(), c2.getProposalsRatio());
                    }
                });

        if (results == null || results.isEmpty()) {
            throw new Exception("Aucune conference evaluee");
        }
        return results;
    }
}
