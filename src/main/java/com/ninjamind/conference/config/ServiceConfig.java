package com.ninjamind.conference.config;

import com.ninjamind.conference.controller.SimpleCORSFilter;
import com.ninjamind.conference.service.ConferenceHandlerEvent;
import com.ninjamind.conference.service.ConferenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ninjamind.conference.service"})
public class ServiceConfig {

}
