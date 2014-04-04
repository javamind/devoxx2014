package com.ninjamind.conference.junit.rule;

import javassist.bytecode.stackmap.TypeData;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public class DbUnitTestRule extends ExternalResource{
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

    public DbUnitTestRule(IDataSet dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Avant chaque test un jeu de donnees est injecte
     *
     * @throws Exception
     */
    @Override
    protected void before() throws Throwable {
        //On recupère
        initProperties();
        cleanlyInsert(dataSet);
    }

    /**
     *
     * @throws IOException
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

    @Override
    protected void after() {

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
            e.printStackTrace();
        }
    }

}
