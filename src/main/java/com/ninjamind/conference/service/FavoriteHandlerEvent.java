package com.ninjamind.conference.service;

import com.google.common.base.Function;
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
 */
public class FavoriteHandlerEvent implements FavoriteService{

    private static Logger LOG = LoggerFactory.make();

    @Autowired
    private ConferenceRepository conferenceRepository;

    protected class ResultConfCalculator{
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
        //Pour le moment nous n'avons pas de critère de filtre dans ReadAllConferenceRequestEvent
        List<Conference> conferences  = conferenceRepository.findAll();

        //On calcule les indicateurs que l'on va retourner dans une liste
        Collection<ResultConfCalculator> results = FluentIterable
                .from(conferences)
                .transform(
                        new Function<Conference, ResultConfCalculator>() {
                            @Override
                            public ResultConfCalculator apply(Conference conference) {
                                //Si une des donnees est vide conf est hors concours
                                if (conference.getNbTwitterFollowers() == null ||
                                        conference.getNbAttendees() == null ||
                                        conference.getNbHourToSellTicket() == null ||
                                        conference.getNbConferenceSlot() == null ||
                                        conference.getNbConferenceProposals() == null) {
                                    LOG.info(String.format("La conference %s n'est pas prise en compte car les donnees ne sont pas toutes renseignees", conference.getName()));
                                    return null;
                                }
                                //Calcul de l'interet speaker
                                Double speakerInterest = BigDecimal.valueOf(conference.getNbConferenceSlot())
                                        .divide(BigDecimal.valueOf(conference.getNbConferenceProposals())).doubleValue();
                                //Cacul interet social
                                Double socialInterest = BigDecimal.valueOf(conference.getNbTwitterFollowers())
                                        .divide(BigDecimal.valueOf(conference.getNbAttendees())).doubleValue();
                                //Cacul interet participant
                                Double attendeeInterest = BigDecimal.valueOf(conference.getNbHourToSellTicket()*60)
                                        .divide(BigDecimal.valueOf(conference.getNbAttendees())).doubleValue();

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
                        int pointConf1 = compSpeaker<=0 ? 1 : 0 ;
                        int pointConf2 = compSpeaker>=0 ? 1 : 0 ;
                        //Si le ratio social est plus fort c'est que les participants sont plus intéresses par la conf car il s'abonnent plus
                        pointConf1 += compSocial>=0 ? 1 : 0 ;
                        pointConf2 += compSocial<=0 ? 1 : 0 ;
                        //Si le ratio participant est plus faible c'est que les participants sont plus intéresses par la conf car il reserve plus vite
                        pointConf1 += compSocial>=0 ? 1 : 0 ;
                        pointConf2 += compSocial<=0 ? 1 : 0 ;

                        return pointConf1-pointConf2;
                    }
                });

        if(results==null || results.isEmpty()) {
            LOG.info("Aucune conference ne ressort gagnante");
            return null;
        }
        return new ReadConferenceEvent(results.iterator().next().conference);
    }
}
