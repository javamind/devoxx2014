package com.ninjamind.conference.service;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.speaker.*;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.repository.SpeakerRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import com.ninjamind.conference.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Gestion des {@link com.ninjamind.conference.domain.Speaker}
 * @author EHRET_G
 */
@Service
@Transactional
public class SpeakerHandlerEvent implements SpeakerService
{
    private static Logger LOG = LoggerFactory.make();

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Recuperation de la liste des speakers
     * @param event
     * @return
     */
    @Override
    public ReadAllSpeakerEvent getAllSpeaker(ReadAllSpeakerRequestEvent event) {
        Preconditions.checkNotNull(event);

        //Pour le moment nous n'avons pas de critère de filtre dans ReadAllSpeakerRequestEvent
        List<Speaker> speakers  = speakerRepository.findAll(sortByNameAsc());

        //Construction du resultat
        ReadAllSpeakerEvent eventReturned = new ReadAllSpeakerEvent();
        eventReturned.populateFromSpeakerList(speakers);
        LOG.debug(String.format("Recuperation de la liste des speakers UUID:%s", eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Recuperation d'un speaker vi a son ID
     * @param event
     * @return
     */
    @Override
    public ReadSpeakerEvent getSpeaker(ReadSpeakerRequestEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "id is required for search speaker");

        //Recherche de l'element par l'id
        Speaker speaker = speakerRepository.findOne(Utils.stringToLong(event.getId()));

        ReadSpeakerEvent eventReturned = new ReadSpeakerEvent(speaker);
        LOG.debug(String.format("Recuperation du speaker ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Creation d'une nouvelle speaker
     * @param event
     * @return
     */
    @Override
    public CreatedSpeakerEvent createSpeaker(CreateSpeakerEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getSpeaker(), "speaker is required to create it");

        CreatedSpeakerEvent eventReturned = new CreatedSpeakerEvent(
                new SpeakerDetail(transformAndSaveSpeakerDetailToSpeaker(event, true)));

        LOG.debug(String.format("Creation du speaker ayant id=[%d] name=[%s] UUID:%s",
                eventReturned.getSpeaker().getId(), eventReturned.getSpeaker().getLastname(),
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Permet de convertir la donnée reçue
     * @param event
     * @return
     */
    private Speaker transformAndSaveSpeakerDetailToSpeaker(CreateSpeakerEvent event, boolean creation) {
        //On cree notre entite Speaker a partir des donnees envoyées
        Speaker speaker = event.getSpeaker().toSpeaker();

        //Le pays envoye est simplement un code on doit mettre a jour le pays avec les données présentes
        //en base de données
        speaker.setCountry(countryRepository.findCountryByCode(speaker.getCountry().getCode()));

        //Si pas en creation on regarde si enreg existe
        if(!creation && speakerRepository.findOne(speaker.getId())==null){
            return null;
        }

        //On enregistre
        return speakerRepository.save(speaker);
    }

    @Override
    public UpdatedSpeakerEvent updateSpeaker(UpdateSpeakerEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getSpeaker(), "speaker is required to update it");


        UpdatedSpeakerEvent eventReturned = new UpdatedSpeakerEvent(transformAndSaveSpeakerDetailToSpeaker(event, false));

        LOG.debug(String.format("Modification du speaker ayant id=[%d] name=[%s] UUID:%s",
                eventReturned.getSpeaker() !=null ? eventReturned.getSpeaker().getId() : null,
                eventReturned.getSpeaker() !=null ? eventReturned.getSpeaker().getLastname() : null,
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Suppression d'une speaker
     * @param event
     * @return
     */
    @Override
    public DeletedSpeakerEvent deleteSpeaker(DeleteSpeakerEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "speaker is required to delete speaker");

        //Recherche de l'element par l'id
        Speaker speaker = speakerRepository.findOne(Utils.stringToLong(event.getId()));
        DeletedSpeakerEvent eventReturned = null;

        if(speaker!=null){
            speakerRepository.delete(speaker);
            eventReturned = new DeletedSpeakerEvent(true, new SpeakerDetail(speaker));
            LOG.debug(String.format("Suppression de la speaker ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        else{
            eventReturned = new DeletedSpeakerEvent(false, null);
            LOG.debug(String.format("La speaker ayant id=[%s] n'existe pas UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        return eventReturned;
    }

    /**
     * Returns a Sort object which sorts speaker in ascending order by using the name.
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }
}
