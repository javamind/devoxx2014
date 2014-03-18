package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Country;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.PropertyValueException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Classe de test du repository {@link CountryRepository}
 * @author agnes
 */
public class CountryRepositoryBadTest extends AbstractJpaRepositoryTest {

    private static final String JDBC_DRIVER = org.postgresql.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/devoxx2014";
    private static final String USER = "devoxx2014";
    private static final String PASSWORD = "devoxx2014";

    /**
     * DataBase DBUnit
     */
    IDatabaseTester databaseTester;

    /**
     * Repository à tester
     */
    @Autowired
    private CountryRepository countryRepository;

    /**
     * Avant chaque test un jeu de données est injecté
     * @throws Exception
     */
    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/init_country.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    /**
     * Test de la classe {@link CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void testFindCountryByCode_OK() {
        Country returnedCountry = countryRepository.findCountryByCode("FRA");
        assertEquals("France", returnedCountry.getName());
    }
    /**
     * Test de la classe {@link CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void testFindCountryByCode_KO() {
        Country returnedCountry = countryRepository.findCountryByCode(null);
        assertNull(returnedCountry);
    }

    /**
     * Test de la classe {@link CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void testFindCountryByCode_KO2() {
        Country returnedCountry = countryRepository.findCountryByCode("ZZZ");
        assertNull(returnedCountry);
    }


    /**
     * Test de la classe {@link CountryRepository#findCountryByNamePart(String)}
     */
    @Test
    public void testFindCountryByNamePart_OK() {
        List<Country> returnedCountry = countryRepository.findCountryByNamePart("Fra%");
        assertNotNull(returnedCountry);
        assertEquals(1,returnedCountry.size());
        assertEquals("FRA", returnedCountry.get(0).getCode());
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité OK
     */
    @Test
    public void testSave_OK() throws Exception{
        Country country = new Country("SPA", "Spain");
        Country returnedCountry = countryRepository.save(country);

        assertNotNull(returnedCountry);
        assertEquals(country.getId(),returnedCountry.getId());

        // Fetch database data after executing your code
//        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
//        ITable actualTable = databaseDataSet.getTable("COUNTRY");
//
//        // Load expected data from an XML dataset
//        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/country_OK.xml"));
//        ITable expectedTable = expectedDataSet.getTable("COUNTRY");
//
//        // Assert actual database table match expected table
//        Assertion.assertEquals(expectedTable, actualTable);

    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité KO
     */
    @Test
    public void testSave_KO() {
        Country country = new Country();
        country.setName("Libelle");
        try {
            countryRepository.save(country);
            fail();
        }
        catch (JpaSystemException e){
            // OK
            assertEquals(PropertyValueException.class,e.getCause().getCause().getClass());
        }
    }

}
