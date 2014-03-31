package com.ninjamind.conference.service.talk;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.dto.TalkDetail;
import com.ninjamind.conference.events.talk.*;
import com.ninjamind.conference.repository.TalkRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}
 *
 * @author EHRET_G
 */
public class TalkHandlerEventTest {
    @Mock
    TalkRepository talkRepository;

    @InjectMocks
    TalkHandlerEvent service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#createTalk(com.ninjamind.conference.events.talk.CreateTalkEvent)
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void createTalkShouldThrownNullPointerExceptionIfArgIsNull(){
        service.createTalk(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#createTalk(com.ninjamind.conference.events.talk.CreateTalkEvent)
     * cas ou pas de talk passe null
     */
    @Test(expected = NullPointerException.class)
    public void createTalkShouldThrownNullPointerExceptionIfTalkSent(){
        service.createTalk(new CreateTalkEvent(null));
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#createTalk(com.ninjamind.conference.events.talk.CreateTalkEvent)
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void createTalkShouldCreateEntityEvenIfNoCountryAdded(){
        //La sauvegarde de la talk retournera une instance avec un id
        Talk talkCreated = new Talk("Le bon testeur il teste... le mauvais testeur il teste...");
        talkCreated.setId(1L);
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        CreatedTalkEvent createdTalkEvent =
                service.createTalk(new CreateTalkEvent(new TalkDetail(null, "Le bon testeur il teste... le mauvais testeur il teste...")));

        assertThat(createdTalkEvent.getTalk().getId()).isEqualTo(1L);
        assertThat(createdTalkEvent.getTalk().getName()).isEqualTo("Le bon testeur il teste... le mauvais testeur il teste...");
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#createTalk(com.ninjamind.conference.events.talk.CreateTalkEvent)
     * cas nominal
     */
    @Test
    public void createTalkShouldCreateEntity(){
        //La sauvegarde de la talk retournera une instance avec un id
        Talk talkCreated = new Talk("Le bon testeur il teste... le mauvais testeur il teste...");
        talkCreated.setId(1L);
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        CreatedTalkEvent createdTalkEvent =
                service.createTalk(new CreateTalkEvent(new TalkDetail(null, "Le bon testeur il teste... le mauvais testeur il teste...")));
        assertThat(createdTalkEvent.getTalk().getId()).isEqualTo(1L);
        assertThat(createdTalkEvent.getTalk().getName()).isEqualTo("Le bon testeur il teste... le mauvais testeur il teste...");

        //Le but est de verifier que la sauvegarde est appelee mais pas la recherche d'entite
        verify(talkRepository, only()).save(any(Talk.class));
        verifyNoMoreInteractions(talkRepository);
    }


    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.events.talk.UpdateTalkEvent)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void updateTalkShouldThrownNullPointerExceptionIfArgIsNull(){
        service.updateTalk(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.events.talk.UpdateTalkEvent)}
     * cas ou pas de talk passe null
     */
    @Test(expected = NullPointerException.class)
    public void updateTalkShouldThrownNullPointerExceptionIfTalkSent(){
        service.updateTalk(new UpdateTalkEvent(null));
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.events.talk.UpdateTalkEvent)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void updateTalkShouldCreateEntityEvenIfNoCountryAdded(){
        //La sauvegarde de la talk retournera une instance avec un id
        Talk talkCreated = new Talk("Le bon testeur il teste... le mauvais testeur il teste...");
        talkCreated.setId(1L);

        //La recherche de l'entite passee renvoie un resultat
        when(talkRepository.findOne(1L)).thenReturn(talkCreated);
        //Sauvegarde
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        UpdatedTalkEvent updatedTalkEvent =
                service.updateTalk(new UpdateTalkEvent(new TalkDetail(1L, "Le bon testeur il teste... le mauvais testeur il teste...")));

        assertThat(updatedTalkEvent.getTalk().getId()).isEqualTo(1L);
        assertThat(updatedTalkEvent.getTalk().getName()).isEqualTo("Le bon testeur il teste... le mauvais testeur il teste...");

        //Le but est de verifier que la sauvegarde et la recherche sont appelees
        verify(talkRepository, times(1)).findOne(1L);
        verifyNoMoreInteractions(talkRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.events.talk.UpdateTalkEvent)}
     * cas ou on ne modifie pas car la donnée n'a pas été trouvée
     */
    @Test
    public void updateTalkShouldCreateNotEntityIfNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(talkRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        UpdatedTalkEvent updatedTalkEvent =
                service.updateTalk(new UpdateTalkEvent(new TalkDetail(1L, "Le bon testeur il teste... le mauvais testeur il teste...")));

        assertThat(updatedTalkEvent.getTalk()).isNull();
        assertThat(updatedTalkEvent.isEntityFound()).isEqualTo(false);

        //Le but est de verifier que seule la recherche est appelee et non la sauvegarde
        verify(talkRepository, only()).findOne(1L);
        verifyNoMoreInteractions(talkRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#deleteTalk(com.ninjamind.conference.events.talk.DeleteTalkEvent)}
     * cas nominal
     */
    @Test
    public void deleteTalkShouldBeOk(){
        //La recherche de l'entite passee renvoie un resultat
        when(talkRepository.findOne(1L)).thenReturn(new Talk("Le bon testeur il teste... le mauvais testeur il teste..."));

        //On appelle notre service de creation
        DeletedTalkEvent deletedTalkEvent = service.deleteTalk(new DeleteTalkEvent("1"));

        assertThat(deletedTalkEvent.getTalk()).isNotNull();

        //Le but est de verifier que la suppression et la recherche sont appelees
        verify(talkRepository, times(1)).findOne(1L);
        verify(talkRepository, times(1)).delete(any(Talk.class));

        verifyNoMoreInteractions(talkRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#deleteTalk(com.ninjamind.conference.events.talk.DeleteTalkEvent)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void deleteTalkShouldCreateNotEntityIfNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(talkRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        DeletedTalkEvent deletedTalkEvent =
                service.deleteTalk(new DeleteTalkEvent("1"));

        assertThat(deletedTalkEvent.getTalk()).isNull();
        assertThat(deletedTalkEvent.isEntityFound()).isEqualTo(false);

        //Le but est de verifier que seule la recherche est appelee et non la suppression
        verify(talkRepository, only()).findOne(1L);
        verifyNoMoreInteractions(talkRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#getTalk(com.ninjamind.conference.events.talk.ReadTalkRequestEvent)}
     * cas nominal
     */
    @Test
    public void getTalkShouldReturnEntity(){
        //La recherche de l'entite passee renvoie un resultat
        when(talkRepository.findOne(1L)).thenReturn(new Talk("Le bon testeur il teste... le mauvais testeur il teste..."));

        //On appelle notre service de creation
        ReadTalkEvent readTalkEvent = service.getTalk(new ReadTalkRequestEvent("1"));

        assertThat(readTalkEvent.getTalk()).isNotNull();
        assertThat(readTalkEvent.getTalk().getName()).isEqualTo("Le bon testeur il teste... le mauvais testeur il teste...");

    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#getTalk(com.ninjamind.conference.events.talk.ReadTalkRequestEvent)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void getTalkShouldNotReturnEntityIfNoEnreg(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(talkRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        ReadTalkEvent readTalkEvent = service.getTalk(new ReadTalkRequestEvent("1"));

        assertThat(readTalkEvent.getTalk()).isNull();
        assertThat(readTalkEvent.isEntityFound()).isEqualTo(false);
    }

}