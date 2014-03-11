package com.ninjamind.conference.controller;

import com.ninjamind.conference.events.conference.ConferenceDetail;
import com.ninjamind.conference.events.conference.ReadConferenceEvent;
import com.ninjamind.conference.events.conference.ReadConferenceRequestEvent;
import com.ninjamind.conference.service.ConferenceService;
import com.ninjamind.conference.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Test du controller {@link com.ninjamind.conference.controller.ConferenceQueriesController}
 * @author ehret_g
 */
public class ConferenceQueriesControllerTest {
    private static final String RESPONSE_BODY = "test";

    MockMvc mockMvc;

    @InjectMocks
    ConferenceQueriesController controller;

    @Mock
    ConferenceService conferenceService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * Test de la recupération d'une conference via l'API REST : <code>/conferences/{id}</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void getConferenceByIdValid_Should_ReturnEntity() throws Exception {
        String idCherche = "1";

        //Le service renvoie une entite
        when(conferenceService.getConference(new ReadConferenceRequestEvent(idCherche))).thenReturn(
                new ReadConferenceEvent(
                        true,
                        new ConferenceDetail(
                                1L,
                                "name",
                                Utils.dateJavaToJson(new Date()),
                                Utils.dateJavaToJson(new Date()))));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conferences/{id}", idCherche))
                .andDo(print())
                .andExpect(content().string(contains("test")))
                .andExpect(status().isOk());
    }


}
