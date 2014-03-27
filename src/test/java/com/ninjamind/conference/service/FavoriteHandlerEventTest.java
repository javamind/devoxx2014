package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.conference.ReadConferenceEvent;
import com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent;
import com.ninjamind.conference.repository.ConferenceRepository;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.fest.assertions.api.Assertions;
import org.joda.time.DateTime;
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
import static org.mockito.Mockito.when;

/**
 * Classe de test de la classe {@link com.ninjamind.conference.service.FavoriteHandlerEvent}. Vous pouvez
 * voir le pendant de cette classe {@link com.ninjamind.conference.service2.FavoriteServiceTest) qui
 * comprend plusieurs chose nuisant à une bonne lisibilité de la classe.
 * <br/>
 * Le but est de montrer
 * <ul>
 * <li>Localisation des tests : classe de test dans le même package que la classe testée</li>
 * <li>Nommage de la classe de test : nom de la classe testée suffixée par Test</li>
 * <li>Nommage des méthodes de tests qui doivent répondre aux questions quoi et pourquoi</li>
 * <li>Granularité un test ne doit tester qu'une seule chose à la fois</li>
 * <li>S'aider des frameworks de test : exemple de {@link org.junit.runners.Parameterized} souvent méconnu</li>
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

    /**
     * Paramètres utilisés dans {@link #getCoolestConference_ShouldReturnInstance(String, Long, Long, Long, Long, Long, String, Long, Long, Long, Long, Long, String)}
     * @return
     */
    protected Object[] conferenceValues(){
        return $(
                $("Devoxx", 1704L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, 500L, 30L, 200L, 850L, "Mix-IT"),
                $("Devoxx", 1704L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, null, 30L, 200L, 850L, "Devoxx"),
                $("Devoxx", 2L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, null, 30L, 200L, 850L, "Devoxx")
        );
    }

    /**
     * Test de la méthode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getCoolestConference(com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent)}
     * cas ou une valeur est retournee
     * @param nameConf1
     * @param nbHourToSellTicketConf1
     * @param nbAttendeesConf1
     * @param nbConferenceSlotConf1
     * @param nbConferenceProposalsConf1
     * @param nbTwitterFollowersConf1
     * @param nameConf2
     * @param nbHourToSellTicketConf2
     * @param nbAttendeesConf2
     * @param nbConferenceSlotConf2
     * @param nbConferenceProposalsConf2
     * @param nbTwitterFollowersConf2
     * @param confExpected
     */
    @Test
    @Parameters(method = "conferenceValues")
    public void getCoolestConference_ShouldReturnInstance(
            String nameConf1,  Long nbHourToSellTicketConf1, Long nbAttendeesConf1,
            Long nbConferenceSlotConf1, Long nbConferenceProposalsConf1, Long nbTwitterFollowersConf1,
            String nameConf2,  Long nbHourToSellTicketConf2, Long nbAttendeesConf2,
            Long nbConferenceSlotConf2, Long nbConferenceProposalsConf2, Long nbTwitterFollowersConf2,
            String confExpected
    ) {
        addConference(nameConf1, nbHourToSellTicketConf1, nbAttendeesConf1, nbConferenceSlotConf1, nbConferenceProposalsConf1, nbTwitterFollowersConf1);
        addConference(nameConf2, nbHourToSellTicketConf2, nbAttendeesConf2, nbConferenceSlotConf2, nbConferenceProposalsConf2, nbTwitterFollowersConf2);
        when(conferenceRepository.findAll()).thenReturn(conferences);

        ReadConferenceEvent event = favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
        Assertions.assertThat(event.getConference().getName()).isEqualTo(confExpected);
    }

    /**
     * Test de la méthode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getCoolestConference(com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent)}
     * cas ou aucune valeur en base de données
     */
    @Test
    public void getCoolestConference_ShouldReturnNull_IfNoDataInDatabase() {
        when(conferenceRepository.findAll()).thenReturn(conferences);
        ReadConferenceEvent event = favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
        Assertions.assertThat(event.getConference()).isNull();
        Assertions.assertThat(event.isEntityFound()).isEqualTo(false);
    }

    /**
     * Test de la méthode {@link com.ninjamind.conference.service.FavoriteHandlerEvent#getCoolestConference(com.ninjamind.conference.events.conference.ReadCoolestConferenceRequestEvent)}
     * cas ou une exception est remontee lors de la recuperation des donnees
     */
    @Test(expected = PersistenceException.class)
    public void getCoolestConference_ShouldThrowException_IfPersistenceProblem() {
        when(conferenceRepository.findAll()).thenThrow(new PersistenceException());
        favoriteHandlerEvent.getCoolestConference(new ReadCoolestConferenceRequestEvent());
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
