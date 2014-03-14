package com.ninjamind.conference.controller;

import com.google.common.collect.Lists;
import com.ninjamind.conference.events.conference.*;
import com.ninjamind.conference.service.ConferenceService;
import com.ninjamind.conference.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Test du controller {@link com.ninjamind.conference.controller.ConferenceQueriesController}
 * @author ehret_g
 */
public class ConferenceQueriesControllerTest {
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
     * Test de la recuperation d'une conference via l'API REST : <code>/conferences/{id}</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldReturnEntityWhenSearchByIdValid() throws Exception {
        String idCherche = "1";

        //Le service renvoie une entite
        when(conferenceService.getConference(any(ReadConferenceRequestEvent.class))).thenReturn(
                new ReadConferenceEvent(
                        true,
                        new ConferenceDetail(
                                Long.valueOf(idCherche),
                                "Mix-IT",
                                Utils.dateJavaToJson(new Date(0)),
                                Utils.dateJavaToJson(new Date(0)))));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conferences/{id}", idCherche))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Mix-IT"))
                .andExpect(status().isOk());
    }

    /**
     * Test de la recuperation d'une conference via l'API REST : <code>/conferences/{id}</code>. On teste le cas oe l'enregistrement
     * n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldReturnNotFoundStatusWhenSearchByIdInvalid() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.getConference(any(ReadConferenceRequestEvent.class))).thenReturn(
                new ReadConferenceEvent(false,null));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conferences/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la recuperation de toutes les conferences via l'API REST : <code>/conferences</code>. On teste le cas passant
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnListEntityWhenSearchAllConference() throws Exception {
        List<ConferenceDetail> listExpected = Lists.newArrayList (
                new ConferenceDetail(
                    Long.valueOf("1"),
                    "Mix-IT",
                    Utils.dateJavaToJson(new Date(0)),
                    Utils.dateJavaToJson(new Date(0))),
                new ConferenceDetail(
                        Long.valueOf("2"),
                        "Devoxx",
                        Utils.dateJavaToJson(new Date(0)),
                        Utils.dateJavaToJson(new Date(0))));

        //Le service renvoie une entite
        when(conferenceService.getAllConference(any(ReadAllConferenceRequestEvent.class))).thenReturn(
                new ReadAllConferenceEvent(listExpected));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conferences"))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("Mix-IT"))
                .andExpect(jsonPath("$[1].name").value("Devoxx"))
                .andExpect(status().isOk());
    }
}
