package com.ninjamind.conference.repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.conference.database.InitializeOperations;
import com.ninjamind.conference.domain.Country;

import static com.ninja_squad.dbsetup.Operations.*;

import static org.fest.assertions.Assertions.*;

import org.fest.assertions.Assertions;
import org.hibernate.PropertyValueException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Classe de test du repository {@link com.ninjamind.conference.repository.CountryRepository}
 * @author ehret_g
 */
public class CountryRepositoryTest extends AbstractJpaRepositoryTest {
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
    public void findCountryByCodeNull_should_returnNull() {
        Country returnedCountry = countryRepository.findCountryByCode(null);
        assertThat(returnedCountry).isNull();
    }

    /**
     * Test de la classe {@link com.ninjamind.conference.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void findCountryByCodeValid_should_returnCountry() {
        Country returnedCountry = countryRepository.findCountryByCode("FRA");
        assertThat(returnedCountry.getName()).isEqualTo("France");
    }

    /**
     * Test de la classe {@link com.ninjamind.conference.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void findCountryByCodeInvalid_should_returnCountry() {
        Country returnedCountry = countryRepository.findCountryByCode("ZZZ");
        assertThat(returnedCountry).isNull();
    }


    /**
     * Test de la classe {@link com.ninjamind.conference.repository.CountryRepository#findCountryByNamePart(String)}
     */
    @Test
    public void findCountryByNamePart_should_returnCountry() {
        List<Country> returnedCountry = countryRepository.findCountryByNamePart("Fra%");
        assertThat(returnedCountry).isNotEmpty().hasSize(1).onProperty("code").containsSequence("FRA");
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité
     */
    @Test
    public void createCountry_should_returnInstanceWithId() {
        Country country = new Country("CODE", "Libelle");
        Country returnedCountry = countryRepository.save(country);

        Assertions.assertThat(returnedCountry.getId()).isNotNull().isEqualTo(country.getId());
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité sans avoir renseigné un champ obligatoire comme
     * le code
     */
    @Test
    public void createCountryWithoutRequiredField_should_returnException() {
        Country country = new Country();
        country.setName("Libelle");
        try {
            countryRepository.save(country);
            Assert.fail();
        }
        catch (JpaSystemException e){
            Assertions.assertThat(e.getCause().getCause()).isInstanceOf(PropertyValueException.class);
        }
    }

}
