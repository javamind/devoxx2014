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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
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
    /**
     * Fichier de properties contenant les paramètres de la base de données
     */
    private Properties properties = new Properties();
    /**
     * DataBase DBUnit
     */
    protected IDatabaseTester databaseTester;
    /**
     * Jeu de donnée
     */
    protected IDataSet dataSet;
    /**
     * Prop JDBC
     */
    protected String databaseJdbcDriver;
    /**
     * Prop JDBC
     */
    protected String databaseUrl;
    /**
     * Prop JDBC
     */
    protected String databaseUsername;
    /**
     * Prop JDBC
     */
    protected String databasePassword;

    /**
     * Avant chaque test un jeu de donnees est injecte
     *
     * @throws Exception
     */
    @BeforeMethod
    public void importDataSet() throws Exception {
        initProperties();
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
     *
     * @throws java.io.IOException
     */
    private void initProperties() throws IOException {
        if(databaseJdbcDriver==null) {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            databaseJdbcDriver = properties.getProperty("db.driver");
            databaseUrl = properties.getProperty("db.url");
            databaseUsername = properties.getProperty("db.username");
            databasePassword = properties.getProperty("db.password");
        }
    }

    /**
     * Connection a la base et preparation du jeu de donnee
     * @param dataSet
     * @throws Exception
     */
    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    /**
     *
     * @param tableName
     * @param pathDataSetExpected
     * @param includedColumns
     */
    public void assertTableInDatabaseIsEqualToXmlDataset(String tableName, String pathDataSetExpected, String ... includedColumns){
        try {
            IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable(tableName);
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File(pathDataSetExpected));
            ITable expectedTable = expectedDataSet.getTable(tableName);
            ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable, includedColumns);
            Assertion.assertEquals(expectedTable, filteredActualTable);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
