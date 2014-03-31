package com.ninjamind.conference.service.talk;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.dto.TalkDetail;
import com.ninjamind.conference.events.talk.*;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import com.ninjamind.conference.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Gestion des {@link com.ninjamind.conference.domain.Talk}
 * @author EHRET_G
 */
@Service
@Transactional
public class TalkHandlerEvent implements TalkService
{
    private static Logger LOG = LoggerFactory.make();

    @Autowired
    private TalkRepository talkRepository;

    /**
     * Recuperation de la liste des talks
     * @param event
     * @return
     */
    @Override
    public ReadAllTalkEvent getAllTalk(ReadAllTalkRequestEvent event) {
        Preconditions.checkNotNull(event);

        //Pour le moment nous n'avons pas de critère de filtre dans ReadAllTalkRequestEvent
        List<Talk> talks  = talkRepository.findAll(sortByNameAsc());

        //Construction du resultat
        ReadAllTalkEvent eventReturned = new ReadAllTalkEvent();
        eventReturned.populateFromTalkList(talks);
        LOG.debug(String.format("Recuperation de la liste des talks UUID:%s", eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Recuperation d'un talk vi a son ID
     * @param event
     * @return
     */
    @Override
    public ReadTalkEvent getTalk(ReadTalkRequestEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "id is required for search talk");

        //Recherche de l'element par l'id
        Talk talk = talkRepository.findOne(Utils.stringToLong(event.getId()));

        ReadTalkEvent eventReturned = new ReadTalkEvent(talk);
        LOG.debug(String.format("Recuperation du talk ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Creation d'une nouvelle talk
     * @param event
     * @return
     */
    @Override
    public CreatedTalkEvent createTalk(CreateTalkEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getTalk(), "talk is required to create it");

        CreatedTalkEvent eventReturned = new CreatedTalkEvent(
                new TalkDetail(transformAndSaveTalkDetailToTalk(event, true)));

        LOG.debug(String.format("Creation du talk ayant id=[%d] name=[%s] UUID:%s",
                eventReturned.getTalk().getId(), eventReturned.getTalk().getName(),
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Permet de convertir la donnée reçue
     * @param event
     * @return
     */
    private Talk transformAndSaveTalkDetailToTalk(CreateTalkEvent event, boolean creation) {
        //On cree notre entite Talk a partir des donnees envoyées
        Talk talk = event.getTalk().toTalk();

        //Si pas en creation on regarde si enreg existe
        if(!creation){
            Talk talkToPersist = talkRepository.findOne(talk.getId());
            if(talkToPersist==null){
                return null;
            }
            talkToPersist.setLevel(talk.getLevel());
            talkToPersist.setNbpeoplemax(talk.getNbpeoplemax());
            talkToPersist.setPlace(talk.getPlace());
            talkToPersist.setDescription(talk.getDescription());
            talkToPersist.setName(talk.getName());
            return talkToPersist;
        }
        else{
            //On enregistre
            return talkRepository.save(talk);
        }
    }

    @Override
    public UpdatedTalkEvent updateTalk(UpdateTalkEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getTalk(), "talk is required to update it");


        UpdatedTalkEvent eventReturned = new UpdatedTalkEvent(transformAndSaveTalkDetailToTalk(event, false));

        LOG.debug(String.format("Modification du talk ayant id=[%d] name=[%s] UUID:%s",
                eventReturned.getTalk() !=null ? eventReturned.getTalk().getId() : null,
                eventReturned.getTalk() !=null ? eventReturned.getTalk().getName() : null,
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Suppression d'une talk
     * @param event
     * @return
     */
    @Override
    public DeletedTalkEvent deleteTalk(DeleteTalkEvent event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "talk is required to delete talk");

        //Recherche de l'element par l'id
        Talk talk = talkRepository.findOne(Utils.stringToLong(event.getId()));
        DeletedTalkEvent eventReturned = null;

        if(talk!=null){
            talkRepository.delete(talk);
            eventReturned = new DeletedTalkEvent(true, new TalkDetail(talk));
            LOG.debug(String.format("Suppression du talk ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        else{
            eventReturned = new DeletedTalkEvent(false, null);
            LOG.debug(String.format("Le talk ayant id=[%s] n'existe pas UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        return eventReturned;
    }

    /**
     * Returns a Sort object which sorts talk in ascending order by using the name.
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }
}
