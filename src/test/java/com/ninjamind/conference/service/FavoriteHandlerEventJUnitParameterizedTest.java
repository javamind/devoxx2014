package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.service.FavoriteHandlerEvent;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Implementation de JUnit Parameterized
 * Pour la bonne implementation voir {@link com.ninjamind.conference.service.FavoriteHandlerEventCibleTest}
 */
@RunWith(Parameterized.class)
public class FavoriteHandlerEventJUnitParameterizedTest {
    @Mock
    private ConferenceRepository conferenceRepository;

    @InjectMocks
    private FavoriteHandlerEvent favoriteHandlerEvent;

    private List<Conference> conferences = new ArrayList<>();


    /**
     * Variables d'instance nécessaires pour JUnit Parameterized
     * Le runner Parameterized injecte les valeurs fournies par la méthode conferenceValues
     * dans des attributs de classe afin de les rendre accessibles aux méthodes de test
     */
    private String nameConf1;
    private Long nbConferenceSlotConf1;
    private Long nbConferenceProposalsConf1;
    private String nameConf2;
    private Long nbConferenceSlotConf2;
    private Long nbConferenceProposalsConf2;
    private String confExpected;

    public FavoriteHandlerEventJUnitParameterizedTest(String nameConf1, Long nbConferenceSlotConf1, Long nbConferenceProposalsConf1, String nameConf2, Long nbConferenceSlotConf2, Long nbConferenceProposalsConf2, String confExpected) {
        this.nameConf1 = nameConf1;
        this.nbConferenceSlotConf1 = nbConferenceSlotConf1;
        this.nbConferenceProposalsConf1 = nbConferenceProposalsConf1;
        this.nameConf2 = nameConf2;
        this.nbConferenceSlotConf2 = nbConferenceSlotConf2;
        this.nbConferenceProposalsConf2 = nbConferenceProposalsConf2;
        this.confExpected = confExpected;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Parameterized.Parameters
    public static Collection<Object[]> conferenceValues() {
        return Arrays.asList(
                //Avec les vraies valeurs Mix-IT est la plus sélective
                new Object[]{"Devoxx2014", 154L, 658L, "Mix-IT2014", 30L, 200L, "Mix-IT2014"},
                //Une conf avec des donnees incomplètes ne compte pas
                new Object[]{"JugSummerCamp2014", 20L, null, "Mix-IT2014", 30L, 200L, "Mix-IT2014"}
        );
    }
    /**
     * Test de la methode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getTheMoreSelectiveConference}
     * cas ou une valeur est retournee
     */
    @Test
    public void shouldFindTheMoreSelectiveConference(){
        addConference(nameConf1, nbConferenceSlotConf1, nbConferenceProposalsConf1);
        addConference(nameConf2, nbConferenceSlotConf2, nbConferenceProposalsConf2);
        when(conferenceRepository.findAll()).thenReturn(conferences);
        Conference theBestConf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assertions.assertThat(theBestConf.getName()).isEqualTo(confExpected);
    }

    /**
     * Test de la methode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getTheMoreSelectiveConference}
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
        conferenceCreated.setNbConferenceSlot(nbConferenceSlot);
        conferenceCreated.setNbConferenceProposals(nbConferenceProposals);
        conferences.add(conferenceCreated);
    }

}
