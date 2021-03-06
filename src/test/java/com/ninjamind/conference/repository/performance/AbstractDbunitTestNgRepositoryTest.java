package com.ninjamind.conference.repository.performance;

import com.ninjamind.conference.config.PersistenceConfig;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe parente de toutes nos classes de tests permettant de charger le contexte Spring des tests propres
 * au repository
 * @author ehret_g
 */
@ContextConfiguration(classes = {PersistenceConfig.class})
public abstract class AbstractDbunitTestNgRepositoryTest extends AbstractTransactionalTestNGSpringContextTests{

    private static Properties properties = new Properties();
    protected IDatabaseTester databaseTester;
    protected static String databaseJdbcDriver;
    protected static String databaseUrl;
    protected static String databaseUsername;
    protected static String databasePassword;


    @BeforeClass
    public static void initProperties() throws IOException {
        if(databaseJdbcDriver==null) {
            properties.load(AbstractDbunitTestNgRepositoryTest.class.getResourceAsStream("/application.properties"));
            databaseJdbcDriver = properties.getProperty("db.driver");
            databaseUrl = properties.getProperty("db.url");
            databaseUsername = properties.getProperty("db.username");
            databasePassword = properties.getProperty("db.password");
        }
    }

    @BeforeMethod
    public void importDataSet() throws Exception {
        initProperties();
        IDataSet dataSet = readDataSet();
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    protected abstract IDataSet readDataSet() throws Exception;

}
