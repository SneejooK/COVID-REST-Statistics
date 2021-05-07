package com.covidstat.repositories;

import com.covidstat.entities.City;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

    List<City> findAll();
    
    City findByCityId(int id);
    
    City findByCityName(String name);
}
