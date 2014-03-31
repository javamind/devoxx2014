package com.ninjamind.conference.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ninjamind.conference.service"})
public class ServiceConfig {

}
