package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Country;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.PropertyValueException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Classe de test du repository {@link CountryRepository}
 *
 * @author agnes
 */
public class CountryRepositoryBadTest extends AbstractDbunitRepositoryTest {
    /**
     * Repository à tester
     */
    @Autowired
    private CountryRepository countryRepository;


    protected IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/init_country.xml"));
    }

    /**
     * Test de la classe {@link CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void testFindCountryByCode_OK() {
        Country persistantCountry = countryRepository.findCountryByCode("FRA");
        assertEquals("France", persistantCountry.getName());
    }

    /**
     * Test de la classe {@link CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void testFindCountryByCode_KO() {
        Country persistantCountry = countryRepository.findCountryByCode(null);
        assertNull(persistantCountry);
    }

    /**
     * Test de la classe {@link CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void testFindCountryByCode_KO2() {
        Country persistantCountry = countryRepository.findCountryByCode("ZZZ");
        assertNull(persistantCountry);
    }


    /**
     * Test de la classe {@link CountryRepository#findCountryByNamePart(String)}
     */
    @Test
    public void testFindCountryByNamePart_OK() {
        List<Country> persistantCountry = countryRepository.findCountryByNamePart("Fra%");
        assertNotNull(persistantCountry);
        assertEquals(1, persistantCountry.size());
        assertEquals("FRA", persistantCountry.get(0).getCode());
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité OK
     * Avec DBUnit le but est de montrer le côté fastidieux de l'écriture du test:
     * - necessite de declarer un transactionTemplate pour avoir une transaction separee et qu'elle soit commitee pour ensuite verifier les donnees
     * - pour les assertions sur les donnees : necessite de filtrer les datasets pour exclure certaines colonnes
     */
    @Test
    public void testSave_OK() throws Exception {
        final Country country = new Country("SPA", "Spain");

        TransactionTemplate tp = new TransactionTemplate(transactionManager);
        tp.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tp.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                Country persistantCountry = countryRepository.save(country);
                assertNotNull(persistantCountry);
                assertEquals(country.getId(), persistantCountry.getId());
                return null;
            }
        });

        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("COUNTRY");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/country_OK.xml"));
        ITable expectedTable = expectedDataSet.getTable("COUNTRY");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id*", "version", "maj*"});
        Assertion.assertEquals(expectedTable, filteredActualTable);

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
        } catch (JpaSystemException e) {
            // OK
            assertEquals(PropertyValueException.class, e.getCause().getCause().getClass());
        }
    }

}
