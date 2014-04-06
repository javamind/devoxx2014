package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
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
 * Pour la bonne implementation voir {@link FavoriteHandlerEventCibleTest}
 */
public class FavoriteHandlerEventTest {
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
     * Test de la methode {@link FavoriteHandlerEvent#getTheMoreSelectiveConference}
     * cas ou une valeur est retournee
     */
    @Test
    public void testTheMoreSelectiveConfOK() throws Exception {
        //Mock
        when(conferenceRepository.findAll()).thenReturn(conferences);

        //Premier cas avec les vraies valeurs
        conferences.clear();
        addConference("Devoxx", 154L, 658L);
        addConference("Mix-IT", 30L, 200L);
        Conference conf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assert.assertEquals("Mix-IT", conf.getName());

        //Si une valeur est nulle la conf n'est pas prise en compte
        conferences.clear();
        addConference("Devoxx", 154L, 658L);
        addConference("Mix-IT", 30L, null);
        conf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assert.assertEquals("Devoxx", conf.getName());

        //Le JUG SummerCamp devrait passer devant
        conferences.clear();
        addConference("Devoxx", 154L, 658L);
        addConference("Mix-IT", 30L, 200L);
        addConference("JUGSummerCamp", 12L, 135L);
        conf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assert.assertEquals("JUGSummerCamp", conf.getName());

    }

    /**
     * Test de la methode {@link FavoriteHandlerEvent#getTheMoreSelectiveConference}
     * cas ou une exception est remontee lors de la recuperation des donnees
     */
    @Test
    public void testTheMoreSelectiveConfKO() {
        when(conferenceRepository.findAll()).thenThrow(new PersistenceException());

        try {
            favoriteHandlerEvent.getTheMoreSelectiveConference();
            Assert.fail();
        }
        catch (PersistenceException e){
            //OK
        } catch (Exception e) {
            Assert.fail();
        }
    }


    /**
     * Permet d'ajouter une conference a la notre liste
     * @param name
     * @param nbConferenceSlot
     * @param nbConferenceProposals
     */
    private void addConference(String name, Long nbConferenceSlot, Long nbConferenceProposals) {
        Conference conferenceCreated = new Conference();
        conferenceCreated.setName(name);
        conferenceCreated.setNbConferenceSlots(nbConferenceSlot);
        conferenceCreated.setNbConferenceProposals(nbConferenceProposals);
        conferences.add(conferenceCreated);
    }

}
