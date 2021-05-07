package com.covidstat.repositories;

import com.covidstat.entities.Country;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findAll();

    Country findByCountryId(int id);

}
