package com.ninjamind.conference.repository;

import com.ninjamind.conference.config.DataBaseConfigTest;
import com.ninjamind.conference.config.PersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.sql.DataSource;

/**
 * Classe parente de toutes nos classes de tests permettant de charger le contexte Spring des tests propres
 * au repository
 * @author ehret_g
 */
@ContextConfiguration(classes = {PersistenceConfig.class})
public abstract class AbstractJpaRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    /**
     * Datasource utilisee dans les tests
     */
    @Autowired
    protected DataSource dataSource;

}
