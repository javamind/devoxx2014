package com.ninjamind.conference.service2;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.ReadConferenceEvent;
import com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.service.FavoriteHandlerEvent;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Mauvaises pratiques.... Pourquoi ??
 * Pour la bonne implementation voir {@link com.ninjamind.conference.service.FavoriteHandlerEventTest}
 */
public class FavoriteServiceTest {
    @Mock
    private ConferenceRepository conferenceRepository;

    @InjectMocks
    private FavoriteHandlerEvent favoriteHandlerEvent;

    private List<Conference> conferences = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test de la méthode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getCoolestConference(com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent)}
     * cas ou une valeur est retournee
     */
    @Test
    public void testGetCoolestConferenceOK() {
        //Mock
        when(conferenceRepository.findAll()).thenReturn(conferences);

        //Premier cas avec les vraies valeurs
        conferences.clear();
        addConference("Devoxx", 1704L, 1500L, 154L, 658L, 2800L);
        addConference("Mix-IT", 43L, 500L, 30L, 200L, 850L);
        ReadConferenceEvent event = favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
        Assert.assertEquals("Mix-IT", event.getConference().getName());

        //Si une valeur est nulle la conf n'est pas prise en compte
        conferences.clear();
        addConference("Devoxx", 1704L, 1500L, 154L, 658L, 2800L);
        addConference("Mix-IT", 43L, null, 30L, 200L, 850L);
        event = favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
        Assert.assertEquals("Devoxx", event.getConference().getName());

        //En dopant les perfs de devoxx ils doivent passer devant
        conferences.clear();
        addConference("Devoxx", 2L, 1500L, 154L, 658L, 2800L);
        addConference("Mix-IT", 43L, 500L, 30L, 200L, 850L);
        event = favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
        Assert.assertEquals("Devoxx", event.getConference().getName());

        //Si les deux ont une valeur manquante on devrait rien à voir
        conferences.clear();
        addConference("Devoxx", 2L, null, 154L, 658L, 2800L);
        addConference("Mix-IT", 43L, null, 30L, 200L, 850L);
        event = favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
        Assert.assertNull(event.getConference());
        Assert.assertFalse(event.isEntityFound());
    }

    /**
     * Test de la méthode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getCoolestConference(com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent)}
     * cas ou une exception est remontee lors de la recuperation des donnees
     */
    @Test
    public void testGetCoolestConferenceKO() {
        when(conferenceRepository.findAll()).thenThrow(new PersistenceException());

        try {
            favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
            Assert.fail();
        }
        catch (PersistenceException e){
            //OK
        }
    }

    /**
     * Permet d'ajouter une conference a la notre liste
     * @param name
     * @param nbHourToSellTicket
     * @param nbAttendees
     * @param nbConferenceSlot
     * @param nbConferenceProposals
     * @param nbTwitterFollowers
     */
    private void addConference(String name, Long nbHourToSellTicket, Long nbAttendees, Long nbConferenceSlot, Long nbConferenceProposals, Long nbTwitterFollowers) {
        Conference conferenceCreated = new Conference(name, new DateTime(2014, 4, 29, 9, 0).toDate(), new DateTime(2014, 4, 30, 19, 0).toDate());
        conferenceCreated.initConferenceStat(nbHourToSellTicket, nbAttendees, nbConferenceSlot, nbConferenceProposals, nbTwitterFollowers);
        conferences.add(conferenceCreated);
    }
}
