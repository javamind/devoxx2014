package com.ninjamind.conference.controller;

import com.ninjamind.conference.service.ConferenceService;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *  Test du controller {@link com.ninjamind.conference.controller.ConferenceCommandsController}
 * @author ehret_g
 */
public class ConferenceCommandsControllerTest extends TestCase {
    MockMvc mockMvc;

    @InjectMocks
    ConferenceCommandsController controller;

    @Mock
    ConferenceService conferenceService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }


}
