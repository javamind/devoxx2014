package com.ninjamind.conference.service;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.*;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import com.ninjamind.conference.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Gestion des {@link com.ninjamind.conference.domain.Conference}
 * @author EHRET_G
 */
@Service
@Transactional
public class ConferenceHandlerEvent implements ConferenceService
{
    private static Logger LOG = LoggerFactory.make();

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    /**
     * Recuperation de la liste des conferences
     * @param event
     * @return
     */
    @Override
    public ReadAllConferenceEvent getAllConference(ReadAllConferenceRequestEvent event) {
        Preconditions.checkNotNull(event);

        //Pour le moment nous n'avons pas de critère de filtre dans ReadAllConferenceRequestEvent
        List<Conference> conferences  = conferenceRepository.findAll(sortByNameAsc());

        //Construction du resultat
        ReadAllConferenceEvent eventReturned = new ReadAllConferenceEvent();
        eventReturned.populateFromConferenceList(conferences);
        LOG.debug(String.format("Recuperation de la liste des conferences UUID:%s", eventReturned.getKey().toString()));

        return eventReturned;
    }


    /**
     * Recuperation d'une conference vi a son ID
     * @param event
     * @return
     */
    @Override
    public ReadConferenceEvent getConference(ReadConferenceRequestEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "id is required for search conference");

        //Recherche de l'element par l'id
        Conference conference = conferenceRepository.findOne(Utils.stringToLong(event.getId()));

        ReadConferenceEvent eventReturned = new ReadConferenceEvent(conference);
        LOG.debug(String.format("Recuperation de la conference ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Creation d'une nouvelle conference
     * @param event
     * @return
     */
    @Override
    public CreatedConferenceEvent createConference(CreateConferenceEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getConference(), "conference is required to create it");

        CreatedConferenceEvent eventReturned = new CreatedConferenceEvent(
                new ConferenceDetail(transformAndSaveConferenceDetailToConference(event, true)));

        LOG.debug(String.format("Creation de la conference ayant id=[%d] name=[%s] UUID:%s",
                eventReturned.getConference().getId(), eventReturned.getConference().getName(),
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Permet de convertir la donnée reçue
     * @param event
     * @return
     */
    private Conference transformAndSaveConferenceDetailToConference(CreateConferenceEvent event, boolean creation) {
        //On cree notre entite Conference a partir des donnees envoyées
        Conference conference = event.getConference().toConference();

        //Le pays envoye est simplement un code on doit mettre a jour le pays avec les données présentes
        //en base de données
        if(conference.getCountry()!=null){
            conference.setCountry(countryRepository.findCountryByCode(conference.getCountry().getCode()));
        }

        //Si pas en creation on regarde si enreg existe
        if(!creation){
            Conference conferenceToPersist = conferenceRepository.findOne(conference.getId());
            if(conferenceToPersist==null){
                return null;
            }
            conferenceToPersist.setCountry(conference.getCountry());
            conferenceToPersist.setPostalCode(conference.getPostalCode());
            conferenceToPersist.setCity(conference.getCity());
            conferenceToPersist.setDateEnd(conference.getDateEnd());
            conferenceToPersist.setDateStart(conference.getDateStart());
            conferenceToPersist.setName(conference.getName());
            conferenceToPersist.setStreetAdress(conference.getStreetAdress());
            return conferenceToPersist;
        }
        else{
            //On enregistre
            return conferenceRepository.save(conference);
        }
    }

    @Override
    public UpdatedConferenceEvent updateConference(UpdateConferenceEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getConference(), "conference is required to update it");


        UpdatedConferenceEvent eventReturned = new UpdatedConferenceEvent(transformAndSaveConferenceDetailToConference(event, false));

        LOG.debug(String.format("Modification de la conference ayant id=[%d] name=[%s] UUID:%s",
                eventReturned.getConference() !=null ? eventReturned.getConference().getId() : null,
                eventReturned.getConference() !=null ? eventReturned.getConference().getName() : null,
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Suppression d'une conference
     * @param event
     * @return
     */
    @Override
    public DeletedConferenceEvent deleteConference(DeleteConferenceEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "conference is required to delete conference");

        //Recherche de l'element par l'id
        Conference conference = conferenceRepository.findOne(Utils.stringToLong(event.getId()));
        DeletedConferenceEvent eventReturned = null;

        if(conference!=null){
            conferenceRepository.delete(conference);
            eventReturned = new DeletedConferenceEvent(true, new ConferenceDetail(conference));
            LOG.debug(String.format("Suppression de la conference ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        else{
            eventReturned = new DeletedConferenceEvent(false, null);
            LOG.debug(String.format("La conference ayant id=[%s] n'existe pas UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        return eventReturned;
    }


    /**
     * Returns a Sort object which sorts conference in ascending order by using the name.
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }


}
