package com.ninjamind.conference.service.speaker;

import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.dto.SpeakerDetail;
import com.ninjamind.conference.events.speaker.*;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.repository.SpeakerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.speaker.SpeakerService}
 *
 * @author EHRET_G
 */
public class SpeakerHandlerEventTest {
    @Mock
    SpeakerRepository speakerRepository;
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    SpeakerHandlerEvent service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.events.speaker.CreateSpeakerEvent)
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void createSpeakerShouldThrownNullPointerExceptionIfArgIsNull(){
        service.createSpeaker(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.events.speaker.CreateSpeakerEvent)
     * cas ou pas de speaker passe null
     */
    @Test(expected = NullPointerException.class)
    public void createSpeakerShouldThrownNullPointerExceptionIfSpeakerSent(){
        service.createSpeaker(new CreateSpeakerEvent(null));
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.events.speaker.CreateSpeakerEvent)
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void createSpeakerShouldCreateEntityEvenIfNoCountryAdded(){
        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La sauvegarde de la speaker retournera une instance avec un id
        Speaker speakerCreated = new Speaker("Martin","Fowler");
        speakerCreated.setId(1L);
        when(speakerRepository.save(any(Speaker.class))).thenReturn(speakerCreated);

        //On appelle notre service de creation
        CreatedSpeakerEvent createdSpeakerEvent =
                service.createSpeaker(new CreateSpeakerEvent(new SpeakerDetail(null, "Martin","Fowler")));

        assertThat(createdSpeakerEvent.getSpeaker().getId()).isEqualTo(1L);
        assertThat(createdSpeakerEvent.getSpeaker().getFirstname()).isEqualTo("Martin");
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.events.speaker.CreateSpeakerEvent)
     * cas nominal
     */
    @Test
    public void createSpeakerShouldCreateEntity(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country("FR", "France"));
        //La sauvegarde de la speaker retournera une instance avec un id
        Speaker speakerCreated = new Speaker("Martin","Fowler");
        speakerCreated.setId(1L);
        when(speakerRepository.save(any(Speaker.class))).thenReturn(speakerCreated);

        //On appelle notre service de creation
        CreatedSpeakerEvent createdSpeakerEvent =
                service.createSpeaker(new CreateSpeakerEvent(new SpeakerDetail(null, "Martin","Fowler")));
        assertThat(createdSpeakerEvent.getSpeaker().getId()).isEqualTo(1L);
        assertThat(createdSpeakerEvent.getSpeaker().getFirstname()).isEqualTo("Martin");

        //Le but est de verifier que la sauvegarde est appelee mais pas la recherche d'entite
        verify(speakerRepository, only()).save(any(Speaker.class));
        verifyNoMoreInteractions(speakerRepository);
    }


    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.events.speaker.UpdateSpeakerEvent)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void updateSpeakerShouldThrownNullPointerExceptionIfArgIsNull(){
        service.updateSpeaker(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.events.speaker.UpdateSpeakerEvent)}
     * cas ou pas de speaker passe null
     */
    @Test(expected = NullPointerException.class)
    public void updateSpeakerShouldThrownNullPointerExceptionIfSpeakerSent(){
        service.updateSpeaker(new UpdateSpeakerEvent(null));
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.events.speaker.UpdateSpeakerEvent)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void updateSpeakerShouldCreateEntityEvenIfNoCountryAdded(){
        //La sauvegarde de la speaker retournera une instance avec un id
        Speaker speakerCreated = new Speaker("Martin","Fowler");
        speakerCreated.setId(1L);

        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La recherche de l'entite passee renvoie un resultat
        when(speakerRepository.findOne(1L)).thenReturn(speakerCreated);
        //Sauvegarde
        when(speakerRepository.save(any(Speaker.class))).thenReturn(speakerCreated);

        //On appelle notre service de creation
        UpdatedSpeakerEvent updatedSpeakerEvent =
                service.updateSpeaker(new UpdateSpeakerEvent(new SpeakerDetail(1L, "Martin","Fowler")));

        assertThat(updatedSpeakerEvent.getSpeaker().getId()).isEqualTo(1L);
        assertThat(updatedSpeakerEvent.getSpeaker().getFirstname()).isEqualTo("Martin");

        //Le but est de verifier que la sauvegarde et la recherche sont appelees
        verify(speakerRepository, times(1)).findOne(1L);
        verifyNoMoreInteractions(speakerRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.events.speaker.UpdateSpeakerEvent)}
     * cas ou on ne modifie pas car la donnée n'a pas été trouvée
     */
    @Test
    public void updateSpeakerShouldCreateNotEntityIfNotFound(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country("FR", "France"));
        //La recherche de l'entite passee renvoie pas de resultat
        when(speakerRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        UpdatedSpeakerEvent updatedSpeakerEvent =
                service.updateSpeaker(new UpdateSpeakerEvent(new SpeakerDetail(1L, "Martin","Fowler")));

        assertThat(updatedSpeakerEvent.getSpeaker()).isNull();
        assertThat(updatedSpeakerEvent.isEntityFound()).isEqualTo(false);

        //Le but est de verifier que seule la recherche est appelee et non la sauvegarde
        verify(speakerRepository, only()).findOne(1L);
        verifyNoMoreInteractions(speakerRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#deleteSpeaker(com.ninjamind.conference.events.speaker.DeleteSpeakerEvent)}
     * cas nominal
     */
    @Test
    public void deleteSpeakerShouldBeOk(){
        //La recherche de l'entite passee renvoie un resultat
        when(speakerRepository.findOne(1L)).thenReturn(new Speaker("Martin","Fowler"));

        //On appelle notre service de creation
        DeletedSpeakerEvent deletedSpeakerEvent = service.deleteSpeaker(new DeleteSpeakerEvent("1"));

        assertThat(deletedSpeakerEvent.getSpeaker()).isNotNull();

        //Le but est de verifier que la suppression et la recherche sont appelees
        verify(speakerRepository, times(1)).findOne(1L);
        verify(speakerRepository, times(1)).delete(any(Speaker.class));

        verifyNoMoreInteractions(speakerRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#deleteSpeaker(com.ninjamind.conference.events.speaker.DeleteSpeakerEvent)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void deleteSpeakerShouldCreateNotEntityIfNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(speakerRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        DeletedSpeakerEvent deletedSpeakerEvent =
                service.deleteSpeaker(new DeleteSpeakerEvent("1"));

        assertThat(deletedSpeakerEvent.getSpeaker()).isNull();
        assertThat(deletedSpeakerEvent.isEntityFound()).isEqualTo(false);

        //Le but est de verifier que seule la recherche est appelee et non la suppression
        verify(speakerRepository, only()).findOne(1L);
        verifyNoMoreInteractions(speakerRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#getSpeaker(com.ninjamind.conference.events.speaker.ReadSpeakerRequestEvent)}
     * cas nominal
     */
    @Test
    public void getSpeakerShouldReturnEntity(){
        //La recherche de l'entite passee renvoie un resultat
        when(speakerRepository.findOne(1L)).thenReturn(new Speaker("Martin","Fowler"));

        //On appelle notre service de creation
        ReadSpeakerEvent readSpeakerEvent = service.getSpeaker(new ReadSpeakerRequestEvent("1"));

        assertThat(readSpeakerEvent.getSpeaker()).isNotNull();
        assertThat(readSpeakerEvent.getSpeaker().getFirstname()).isEqualTo("Martin");

    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#getSpeaker(com.ninjamind.conference.events.speaker.ReadSpeakerRequestEvent)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void getSpeakerShouldNotReturnEntityIfNoEnreg(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(speakerRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        ReadSpeakerEvent readSpeakerEvent = service.getSpeaker(new ReadSpeakerRequestEvent("1"));

        assertThat(readSpeakerEvent.getSpeaker()).isNull();
        assertThat(readSpeakerEvent.isEntityFound()).isEqualTo(false);
    }

}