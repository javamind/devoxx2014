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
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test de la class {@link FavoriteHandlerEvent}
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
        conferences.add(new Conference("Devoxx2014", 154L, 658L));
        conferences.add(new Conference("Mix-IT2014", 30L, 200L));
        Conference conf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assert.assertEquals("Mix-IT2014", conf.getName());

        //Si une valeur est nulle la conf n'est pas prise en compte
        conferences.clear();
        conferences.add(new Conference("Mix-IT2014", 154L, 658L));
        conferences.add(new Conference("Mix-IT2014", null, 200L));
        conf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assert.assertEquals("Mix-IT2014", conf.getName());

        //Le JUG SummerCamp devrait passer devant
        conferences.clear();
        conferences.add(new Conference("Devoxx2014", 154L, 658L));
        conferences.add(new Conference("Mix-IT2014", 30L, 200L));
        conferences.add(new Conference("JUGSummerCamp2014", 12L, 135L));
        conf = favoriteHandlerEvent.getTheMoreSelectiveConference();
        Assert.assertEquals("JUGSummerCamp2014", conf.getName());

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
     * Test de la methode {@link FavoriteHandlerEvent#getTheMoreSelectiveConference}
     * cas ou aucune conference n'existe
     */
    @Test
    public void testTheMoreSelectiveConfKO2() {
        when(conferenceRepository.findAll()).thenReturn(conferences);

        try {
            favoriteHandlerEvent.getTheMoreSelectiveConference();
            Assert.fail();
        }
        catch (Exception e) {
            //OK
        }
    }

    /**
     * Test de la methode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getTheHypestConfs()}
     * cas ou une valeur est retournee
     */
    @Test
    public void testTheHypestConfConfOK() throws Exception {
        Conference devoxx2014 = new Conference("Devoxx2014", 154L, 658L);
        devoxx2014.setNbTwitterFollowers(2820L);

        Conference mixit2014 = new Conference("Mix-IT2014", 30L, 200L);
        mixit2014.setNbTwitterFollowers(845L);

        conferences.add(devoxx2014);
        conferences.add(mixit2014);

        //Mock
        when(conferenceRepository.findAll()).thenReturn(conferences);

        // extract the names ...
        List<Conference> theHypestConfs = favoriteHandlerEvent.getTheHypestConfs();
        List<String> confNames = new ArrayList<String>();
        for (Conference conf : theHypestConfs) {
            confNames.add(conf.getName());
        }
        List<String> expected = Arrays.asList("Devoxx2014","Mix-IT2014");
        assertEquals(expected,confNames);
    }

}
