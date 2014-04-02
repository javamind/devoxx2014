package com.ninjamind.conference.repository;

import com.ninjamind.conference.config.DataBaseConfigTest;
import com.ninjamind.conference.config.PersistenceConfig;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;

/**
 * Classe parente de toutes nos classes de tests permettant de charger le contexte Spring des tests propres
 * au repository
 * @author ehret_g
 */
@ContextConfiguration(classes = {PersistenceConfig.class, DataBaseConfigTest.class})
public abstract class AbstractDbunitRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    protected String databaseJdbcDriver;
    @Autowired
    protected String databaseUrl;
    @Autowired
    protected String databaseUsername;
    @Autowired
    protected String databasePassword;

    /**
     * DataBase DBUnit
     */
    protected IDatabaseTester databaseTester;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    /**
     * Avant chaque test un jeu de donn�es est inject�
     *
     * @throws Exception
     */
    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);
    }

    /**
     * Template methode a implementer dans les classes de test
     * @return
     * @throws Exception
     */
    protected abstract IDataSet readDataSet() throws Exception;

    /**
     * Connectionn � la base et preparation du jeu de donn�e
     * @param dataSet
     * @throws Exception
     */
    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }
}
