package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Country;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * @author ehret_g
 */
public class CountryRepositoryTest extends AbstractJpaRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    @Transactional
    public void createCountry() {
        Country country = new Country("CODE", "Libelle");
        Country returnedAccount = countryRepository.save(country);

        System.out.printf("account ID is %d and for returned account ID is %d\n", country.getId(), returnedAccount.getId());
    }
}
