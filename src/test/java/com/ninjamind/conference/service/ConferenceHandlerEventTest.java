package com.ninjamind.conference.service;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Level;
import com.ninjamind.conference.events.conference.*;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.utils.Utils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.ConferenceService}
 *
 * @author EHRET_G
 */
public class ConferenceHandlerEventTest {
    @Mock
    ConferenceRepository conferenceRepository;
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    ConferenceHandlerEvent service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#createConference(com.ninjamind.conference.events.conference.CreateConferenceEvent)
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void createConferenceShouldThrownNullPointerExceptionIfArgIsNull(){
        service.createConference(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#createConference(com.ninjamind.conference.events.conference.CreateConferenceEvent)
     * cas ou pas de conference passe null
     */
    @Test(expected = NullPointerException.class)
    public void createConferenceShouldThrownNullPointerExceptionIfConferenceSent(){
        service.createConference(new CreateConferenceEvent(null));
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#createConference(com.ninjamind.conference.events.conference.CreateConferenceEvent)
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void createConferenceShouldCreateEntityEvenIfNoCountryAdded(){
        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La sauvegarde de la conference retournera une instance avec un id
        Conference conferenceCreated = new Conference("Mix-IT", new DateTime(2014,4,29,9,0).toDate(), new DateTime(2014,4,30,19,0).toDate());
        conferenceCreated.setId(1L);
        when(conferenceRepository.save(any(Conference.class))).thenReturn(conferenceCreated);

        //On appelle notre service de creation
        CreatedConferenceEvent createdConferenceEvent =
                service.createConference(new CreateConferenceEvent(new ConferenceDetail(null, "Mix-IT", "2014-04-29 09:00:00", "2014-04-30 19:00:00")));

        assertThat(createdConferenceEvent.getConference().getId()).isEqualTo(1L);
        assertThat(createdConferenceEvent.getConference().getDateStart()).isEqualTo("2014-04-29 09:00:00");
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#createConference(com.ninjamind.conference.events.conference.CreateConferenceEvent)
     * cas nominal
     */
    @Test
    public void createConferenceShouldCreateEntity(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country("FR", "France"));
        //La sauvegarde de la conference retournera une instance avec un id
        Conference conferenceCreated = new Conference("Mix-IT", new DateTime(2014,4,29,9,0).toDate(), new DateTime(2014,4,30,19,0).toDate());
        conferenceCreated.setId(1L);
        when(conferenceRepository.save(any(Conference.class))).thenReturn(conferenceCreated);

        //On appelle notre service de creation
        CreatedConferenceEvent createdConferenceEvent =
                service.createConference(new CreateConferenceEvent(new ConferenceDetail(null, "Mix-IT", null,
                        null,null,"FR", "2014-04-29 09:00:00", "2014-04-30 19:00:00")));
        assertThat(createdConferenceEvent.getConference().getId()).isEqualTo(1L);
        assertThat(createdConferenceEvent.getConference().getDateStart()).isEqualTo("2014-04-29 09:00:00");

        //Le but est de verifier que la sauvegarde est appelee mais pas la recherche d'entite
        verify(conferenceRepository, only()).save(any(Conference.class));
        verifyNoMoreInteractions(conferenceRepository);
    }


    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#updateConference(com.ninjamind.conference.events.conference.UpdateConferenceEvent)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void updateConferenceShouldThrownNullPointerExceptionIfArgIsNull(){
        service.updateConference(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#updateConference(com.ninjamind.conference.events.conference.UpdateConferenceEvent)}
     * cas ou pas de conference passe null
     */
    @Test(expected = NullPointerException.class)
    public void updateConferenceShouldThrownNullPointerExceptionIfConferenceSent(){
        service.updateConference(new UpdateConferenceEvent(null));
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#updateConference(com.ninjamind.conference.events.conference.UpdateConferenceEvent)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void updateConferenceShouldCreateEntityEvenIfNoCountryAdded(){
        //La sauvegarde de la conference retournera une instance avec un id
        Conference conferenceCreated = new Conference("Mix-IT", new DateTime(2014,4,29,9,0).toDate(), new DateTime(2014,4,30,19,0).toDate());
        conferenceCreated.setId(1L);

        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La recherche de l'entite passee renvoie un resultat
        when(conferenceRepository.findOne(1L)).thenReturn(conferenceCreated);
        //Sauvegarde
        when(conferenceRepository.save(any(Conference.class))).thenReturn(conferenceCreated);

        //On appelle notre service de creation
        UpdatedConferenceEvent updatedConferenceEvent =
                service.updateConference(new UpdateConferenceEvent(new ConferenceDetail(1L, "Mix-IT", "2014-04-29 09:00:00", "2014-04-30 19:00:00")));

        assertThat(updatedConferenceEvent.getConference().getId()).isEqualTo(1L);
        assertThat(updatedConferenceEvent.getConference().getDateStart()).isEqualTo("2014-04-29 09:00:00");

        //Le but est de verifier que la recherche est appelee
        verify(conferenceRepository, times(1)).findOne(1L);
        verifyNoMoreInteractions(conferenceRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#updateConference(com.ninjamind.conference.events.conference.UpdateConferenceEvent)}
     * cas ou on ne modifie pas car la donnée n'a pas été trouvée
     */
    @Test
    public void updateConferenceShouldCreateNotEntityIfNotFound(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country("FR", "France"));
        //La recherche de l'entite passee renvoie pas de resultat
        when(conferenceRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        UpdatedConferenceEvent updatedConferenceEvent =
                service.updateConference(new UpdateConferenceEvent(new ConferenceDetail(1L, "Mix-IT", null,
                        null, null, "FR", "2014-04-29 09:00:00", "2014-04-30 19:00:00")));

        assertThat(updatedConferenceEvent.getConference()).isNull();
        assertThat(updatedConferenceEvent.isEntityFound()).isEqualTo(false);

        //Le but est de verifier que seule la recherche est appelee et non la sauvegarde
        verify(conferenceRepository, only()).findOne(1L);
        verifyNoMoreInteractions(conferenceRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#deleteConference(com.ninjamind.conference.events.conference.DeleteConferenceEvent)}
     * cas nominal
     */
    @Test
    public void deleteConferenceShouldBeOk(){
        //La recherche de l'entite passee renvoie un resultat
        when(conferenceRepository.findOne(1L)).thenReturn(new Conference("Mix-IT", new DateTime(2014,4,29,9,0).toDate(), new DateTime(2014,4,30,19,0).toDate()));

        //On appelle notre service de creation
        DeletedConferenceEvent deletedConferenceEvent = service.deleteConference(new DeleteConferenceEvent("1"));

        assertThat(deletedConferenceEvent.getConference()).isNotNull();

        //Le but est de verifier que la suppression et la recherche sont appelees
        verify(conferenceRepository, times(1)).findOne(1L);
        verify(conferenceRepository, times(1)).delete(any(Conference.class));

        verifyNoMoreInteractions(conferenceRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#deleteConference(com.ninjamind.conference.events.conference.DeleteConferenceEvent)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void deleteConferenceShouldCreateNotEntityIfNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(conferenceRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        DeletedConferenceEvent deletedConferenceEvent =
                service.deleteConference(new DeleteConferenceEvent("1"));

        assertThat(deletedConferenceEvent.getConference()).isNull();
        assertThat(deletedConferenceEvent.isEntityFound()).isEqualTo(false);

        //Le but est de verifier que seule la recherche est appelee et non la suppression
        verify(conferenceRepository, only()).findOne(1L);
        verifyNoMoreInteractions(conferenceRepository);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#getConference(com.ninjamind.conference.events.conference.ReadConferenceRequestEvent)}
     * cas nominal
     */
    @Test
    public void getConferenceShouldReturnEntity(){
        //La recherche de l'entite passee renvoie un resultat
        when(conferenceRepository.findOne(1L)).thenReturn(new Conference("Mix-IT", new DateTime(2014,4,29,9,0).toDate(), new DateTime(2014,4,30,19,0).toDate()));

        //On appelle notre service de creation
        ReadConferenceEvent readConferenceEvent = service.getConference(new ReadConferenceRequestEvent("1"));

        assertThat(readConferenceEvent.getConference()).isNotNull();
        assertThat(readConferenceEvent.getConference().getDateStart()).isEqualTo("2014-04-29 09:00:00");

    }

    /**
     * Test de {@link com.ninjamind.conference.service.ConferenceService#getConference(com.ninjamind.conference.events.conference.ReadConferenceRequestEvent)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void getConferenceShouldNotReturnEntityIfNoEnreg(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(conferenceRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        ReadConferenceEvent readConferenceEvent = service.getConference(new ReadConferenceRequestEvent("1"));

        assertThat(readConferenceEvent.getConference()).isNull();
        assertThat(readConferenceEvent.isEntityFound()).isEqualTo(false);
    }

}