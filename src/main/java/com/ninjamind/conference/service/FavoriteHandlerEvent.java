package com.ninjamind.conference.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.ReadConferenceEvent;
import com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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

    protected class ResultConfCalculator {
        private Conference conference;
        private Double speakerInterest;
        private Double socialInterest;
        private Double attendeeInterest;

        public ResultConfCalculator(Conference conference, Double speakerInterest, Double socialInterest, Double attendeeInterest) {
            this.conference = conference;
            this.speakerInterest = speakerInterest;
            this.socialInterest = socialInterest;
            this.attendeeInterest = attendeeInterest;
        }
    }

    @Override
    public ReadConferenceEvent getCoolestConference(ReadCoolestConferenceRequestEvent event) {
        //Recuperation de la liste des conferences
        //Pour le moment nous n'avons pas de crit�re de filtre dans ReadAllConferenceRequestEvent
        List<Conference> conferences = conferenceRepository.findAll();

        //On calcule les indicateurs que l'on va retourner dans une liste
        Collection<ResultConfCalculator> results = FluentIterable
                .from(conferences)
                .filter(new Predicate<Conference>() {
                    @Override
                    public boolean apply(Conference conference) {
                        //Si une des donnees est vide conf est hors concours
                        if (conference.getNbTwitterFollowers() == null ||
                                conference.getNbAttendees() == null ||
                                conference.getNbHourToSellTicket() == null ||
                                conference.getNbConferenceSlot() == null ||
                                conference.getNbConferenceProposals() == null) {
                            LOG.info(String.format("La conference %s n'est pas prise en compte car les donnees ne sont pas toutes renseignees", conference.getName()));
                            return false;
                        }
                        return true;
                    }
                })
                .transform(
                        new Function<Conference, ResultConfCalculator>() {
                            @Override
                            public ResultConfCalculator apply(Conference conference) {
                                //Calcul de l'interet speaker
                                Double speakerInterest = conference.getNbConferenceSlot().doubleValue() / conference.getNbConferenceProposals();
                                //Cacul interet social
                                Double socialInterest = conference.getNbTwitterFollowers().doubleValue() / conference.getNbAttendees();
                                //Cacul interet participant
                                Double attendeeInterest = conference.getNbHourToSellTicket().doubleValue() * 60 / conference.getNbAttendees();

                                return new ResultConfCalculator(conference, speakerInterest, socialInterest, attendeeInterest);
                            }
                        }
                )
                .toSortedList(new Comparator<ResultConfCalculator>() {
                    @Override
                    public int compare(ResultConfCalculator o1, ResultConfCalculator o2) {
                        int compSpeaker = o1.speakerInterest.compareTo(o2.speakerInterest);
                        int compSocial = o1.socialInterest.compareTo(o2.socialInterest);
                        int compAttendee = o1.attendeeInterest.compareTo(o2.attendeeInterest);

                        //Si le ratio speaker est plus faible c'est que l'interet speaker est plus grand puisqu'il propose plus
                        int pointConf1 = compSpeaker <= 0 ? 1 : 0;
                        int pointConf2 = compSpeaker >= 0 ? 1 : 0;
                        //Si le ratio social est plus fort c'est que les participants sont plus int�resses par la conf car il s'abonnent plus
                        pointConf1 += compSocial >= 0 ? 1 : 0;
                        pointConf2 += compSocial <= 0 ? 1 : 0;
                        //Si le ratio participant est plus faible c'est que les participants sont plus int�resses par la conf car il reserve plus vite
                        pointConf1 += compAttendee <= 0 ? 1 : 0;
                        pointConf2 += compAttendee >= 0 ? 1 : 0;

                        return pointConf2 - pointConf1;
                    }
                });

        if (results == null || results.isEmpty()) {
            LOG.info("Aucune conference ne ressort gagnante");
            return new ReadConferenceEvent(null);
        }
        return new ReadConferenceEvent(results.iterator().next().conference);
    }

    @Override
    public Conference getTheMoreSelectiveConference() {
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
            LOG.info("Aucune conference ne ressort gagnante");
            return null;
        }
        return results.iterator().next();
    }
}
