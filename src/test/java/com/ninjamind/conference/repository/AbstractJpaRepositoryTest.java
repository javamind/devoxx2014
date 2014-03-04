package com.ninjamind.conference.repository;

import com.ninjamind.conference.config.PersistenceConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;

/**
 * Classe parente de toutes nos classes de tests permettant de charger le contexte Spring des tests propres
 * au repository
 * @author ehret_g
 */
@TestExecutionListeners({TransactionalTestExecutionListener.class})
@ContextConfiguration(classes = PersistenceConfig.class)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractJpaRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {


}
