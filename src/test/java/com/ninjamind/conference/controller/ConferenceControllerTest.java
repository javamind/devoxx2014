package com.ninjamind.conference.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author ehret_g
 */
public class ConferenceControllerTest {
    private static final String RESPONSE_BODY = "test";

    MockMvc mockMvc;

    @InjectMocks
    ConferenceController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(content().string(RESPONSE_BODY));
    }
}
