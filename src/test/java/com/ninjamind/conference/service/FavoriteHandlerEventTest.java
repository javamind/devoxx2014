package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.when;

/**
 * Classe de test de la classe {@link com.ninjamind.conference.service.FavoriteHandlerEvent}. Vous pouvez
 * voir le pendant de cette classe {@link com.ninjamind.conference.TestFavoriteService ) qui
 * comprend plusieurs chose nuisant a une bonne lisibilite de la classe.
 * <br/>
 * Le but est de montrer
 * <ul>
 * <li>Localisation des tests : classe de test dans le meme package que la classe testee</li>
 * <li>Nommage de la classe de test : nom de la classe testee suffixee par Test</li>
 * <li>Nommage des methodes de tests qui doivent repondre aux questions quoi et pourquoi</li>
 * <li>Granularite un test ne doit tester qu'une seule chose a la fois</li>
 * <li>S'aider des frameworks de test : exemple de {@link org.junit.runners.Parameterized} ou JUnitParamsRunner souvent m�connu</li>
 * <li>Des assertions simples</li>
 * <li>try/catch exception</li>
 * </ul>
 *
 * @author EHRET_G
 */
@RunWith(JUnitParamsRunner.class)
public class FavoriteHandlerEventTest {
    @Mock
    private ConferenceRepository conferenceRepository;

    @InjectMocks
    private FavoriteHandlerEvent favoriteHandlerEvent;

    private List<Conference> conferences = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        conferences.clear();
    }



    protected Object[] conferenceLessValues(){
        return $(
                $("Devoxx2014", 154L, 658L, "Mix-IT2014", 30L, 200L, "Mix-IT2014"),
                $("JugSummerCamp2014", 20L, 140L, "Mix-IT2014", 30L, 200L, "JugSummerCamp2014")
        );
    }

    @Test
    @Parameters(method = "conferenceLessValues")
    public void getTheMoreSelectiveConference_shouldReturnInstance(String nameConf1, Long nbConferenceSlotConf1, Long nbConferenceProposalsConf1,
                                                                   String nameConf2, Long nbConferenceSlotConf2, Long nbConferenceProposalsConf2,
                                                                   String confExpected){
        addConference(nameConf1, nbConferenceSlotConf1, nbConferenceProposalsConf1);
        addConference(nameConf2, nbConferenceSlotConf2, nbConferenceProposalsConf2);
        when(conferenceRepository.findAll()).thenReturn(conferences);
        Conference theBestConf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assertions.assertThat(theBestConf.getName()).isEqualTo(confExpected);

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
