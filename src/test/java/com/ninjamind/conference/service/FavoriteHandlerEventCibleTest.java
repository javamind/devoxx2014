package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Classe de test de la classe {@link com.ninjamind.conference.service.FavoriteHandlerEvent}.
 * <br/>
 * Le but est de montrer
 * <ul>
 * <li>Localisation des tests : classe de test dans le meme package que la classe testee</li>
 * <li>Nommage de la classe de test : nom de la classe testee suffixee par Test</li>
 * <li>Nommage des methodes de tests qui doivent repondre aux questions quoi et pourquoi</li>
 * <li>Granularite un test ne doit tester qu'une seule chose a la fois</li>
 * <li>S'aider des frameworks de test : exemple de JUnitParamsRunner souvent meconnu</li>
 * <li>Des assertions simples</li>
 * <li>try/catch exception</li>
 * </ul>
 *
 * @author EHRET_G
 * @author Agnès
 */
@RunWith(JUnitParamsRunner.class)
public class FavoriteHandlerEventCibleTest {
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

    /**
     * Methode JUnitParams permettant d'injecter les valeurs de tests dans une méthode de tests
     *
     * @return les paramètres des tests
     */
    protected Object[] conferenceValues() {
        Conference devoxx2014 = new Conference("Devoxx2014", 154L, 658L);
        Conference mixit2014 = new Conference("Mix-IT2014", 30L, 200L);
        Conference jugsummercamp2014 = new Conference("JugSummerCamp2014", 10L, 130L);
        Conference mixit2014_withoutNbSlot = new Conference("Mix-IT2014", null, 130L);
        return $(
                //Avec les vraies valeurs Mix-IT est la plus sélective
                $(devoxx2014, mixit2014, mixit2014),
                //Avec les vraies valeurs jugsummercamp est la plus sélective
                $(jugsummercamp2014, mixit2014, jugsummercamp2014),
                //Une conf avec des donnees incomplètes ne compte pas
                $(mixit2014_withoutNbSlot, devoxx2014, devoxx2014)

        );
    }

    /**
     * Test de la recuperation de la conference dans des cas OK
     * Test paramétré : les données de la methode conferenceValues lui sont injectés.
     * A l'execution : autant d'execution que
     */
    @Test
    @Parameters(method = "conferenceValues")
    public void shouldFindTheMoreSelectiveConference(Conference conf1, Conference conf2, Conference confExpected) throws Exception {
        conferences.add(conf1);
        conferences.add(conf2);
        when(conferenceRepository.findAll()).thenReturn(conferences);
        Conference theBestConf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        assertThat(theBestConf.getName()).isEqualTo(confExpected.getName());
    }

    /**
     * Test de la recuperation de la conference la plus selective dans le cas où un paramètre d'une conference n'est pas donné
     * Dans ce cas-là cette conférence n'est pas prise en compte
     */
    @Test
    public void shouldIgnoreTheConfIfOneParamIsMissing() throws Exception {
        Conference devoxx2014 = new Conference("Devoxx2014", 154L, 658L);
        Conference mixit2014 = new Conference("Mix-IT2014", null, 200L);
        conferences.add(devoxx2014);
        conferences.add(mixit2014);
        when(conferenceRepository.findAll()).thenReturn(conferences);
        assertThat(favoriteHandlerEvent.getTheMoreSelectiveConference().getName()).isEqualTo("Devoxx2014");

    }

    /**
     * Test de la recuperation de la conference la plus selective dans le cas où on a une pb avec le repository:
     * le repository lève une exception unchecked de type PersistenceException
     * Dans ce cas là pas de message d'exception à vérifier : autant utiliser simplement 'expected' dans l'annotaiton
     */
    @Test(expected = PersistenceException.class)
    public void shouldThrowExceptionWhenProblemOnDatabase() throws Exception {
        when(conferenceRepository.findAll()).thenThrow(new PersistenceException());
        favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assertions.failBecauseExceptionWasNotThrown(PersistenceException.class);

    }

    /**
     * Test de la recuperation de la conference la plus selective dans le cas où aucune conference n'est à evaluer
     * Doit retourner une exception avec un message "Aucune conference evaluée"
     * Dans ce cas là si le message de l'exception est à vérifier : mieux faire un try-catch et un assert sur le message
     */
    @Test()
    public void shouldThrowExceptionWhenNoConf() {
        when(conferenceRepository.findAll()).thenReturn(conferences);
        try {
            favoriteHandlerEvent.getTheMoreSelectiveConference();
            Assertions.failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception e) {
            assertThat(e).hasMessage("Aucune conference evaluée").hasNoCause();
        }
    }

    @Test
    public void shouldFindTheHypestConf() throws Exception {
        Conference devoxx2014 = new Conference("Devoxx2014", 154L, 658L);
        devoxx2014.setNbTwitterFollowers(2820L);

        Conference mixit2014 = new Conference("Mix-IT2014", 30L, 200L);
        mixit2014.setNbTwitterFollowers(845L);

        conferences.add(devoxx2014);
        conferences.add(mixit2014);

        //Mock
        when(conferenceRepository.findAll()).thenReturn(conferences);

        assertThat(favoriteHandlerEvent.getTheHypestConfs()).extracting("name").contains("Mix-IT2014", "Devoxx2014");

    }


}
