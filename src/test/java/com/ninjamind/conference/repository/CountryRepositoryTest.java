package com.ninjamind.conference.repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.conference.config.PersistenceConfig;
import com.ninjamind.conference.database.InitializeOperations;
import com.ninjamind.conference.domain.Country;
import org.assertj.core.api.Assertions;
import org.hibernate.PropertyValueException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Classe de test du repository {@link com.ninjamind.conference.repository.CountryRepository}
 * @author ehret_g
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional
public class CountryRepositoryTest {
    /**
     * Datasource utilisee dans les tests
     */
    @Autowired
    protected DataSource dataSource;

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
    public void prepare() throws Exception {
        Operation operation =
                sequenceOf(
                        deleteAllFrom("country"),
                        InitializeOperations.INSERT_COUNTRY_DATA);
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    /**
     * Test de la classe {@link com.ninjamind.conference.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void shouldNotFindCountryWhenCodeIsNull() {
        Country persistantCountry = countryRepository.findCountryByCode(null);
        assertThat(persistantCountry).isNull();
    }

    /**
     * Test de la classe {@link com.ninjamind.conference.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void shouldFindCountryWhenCodeIsKnown() {
        Country persistantCountry = countryRepository.findCountryByCode("FRA");
        assertThat(persistantCountry.getName()).isEqualTo("France");
    }

    /**
     * Test de la classe {@link com.ninjamind.conference.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void shouldNotFindCountryWhenCodeIsUnknown() {
        Country persistantCountry = countryRepository.findCountryByCode("ZZZ");
        assertThat(persistantCountry).isNull();
    }


    /**
     * Test de la classe {@link com.ninjamind.conference.repository.CountryRepository#findCountryByNamePart(String)}
     */
    @Test
    public void shouldFindCountryWhenNameIsKnown() {
        List<Country> countriesStartByFra = countryRepository.findCountryByNamePart("Fra%");
        assertThat(countriesStartByFra).isNotEmpty().hasSize(1);
        assertThat(countriesStartByFra).extracting("code").containsExactly("FRA");
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité
     */
    @Test
    public void shouldCreateCountry() {
        Country country = new Country("CODE", "Libelle");
        Country persistantCountry = countryRepository.save(country);

        assertThat(persistantCountry.getId()).isNotNull().isEqualTo(country.getId());
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité sans avoir renseigné un champ obligatoire comme
     * le code
     */
    @Test
    public void shouldNotCreateCountryWhenRequiredFieldIsEmpty() {
        Country country = new Country();
        country.setName("Libelle");
        try {
            countryRepository.save(country);
            failBecauseExceptionWasNotThrown(IndexOutOfBoundsException.class);
        }
        catch (JpaSystemException e){
            Assertions.assertThat(e.getCause().getCause()).isInstanceOf(PropertyValueException.class);
        }
    }

}
