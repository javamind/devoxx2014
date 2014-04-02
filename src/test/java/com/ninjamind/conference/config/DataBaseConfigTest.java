package com.ninjamind.conference.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
@PropertySource("classpath:application.properties")
public class DataBaseConfigTest {
    @Resource
    private Environment env;

    @Bean
    public String databaseJdbcDriver() {
        return env.getRequiredProperty(PersistenceConfig.PROPERTY_NAME_DATABASE_DRIVER);
    }

    @Bean
    public String databaseUrl() {
        return env.getRequiredProperty(PersistenceConfig.PROPERTY_NAME_DATABASE_URL);
    }

    @Bean
    public String databaseUsername() {
        return env.getRequiredProperty(PersistenceConfig.PROPERTY_NAME_DATABASE_USERNAME);
    }

    @Bean
    public String databasePassword() {
        return env.getRequiredProperty(PersistenceConfig.PROPERTY_NAME_DATABASE_PASSWORD);
    }
}
