package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author ehret_g
 */
public interface CountryRepository extends JpaRepository<Country, Long>{

    @Query(value = "SELECT c FROM Country c WHERE c.code = :code")
    Country findCountryByCode(@Param("code") String code);

}
