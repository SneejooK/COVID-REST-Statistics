package com.covidstat.services;

import com.covidstat.entities.City;
import com.covidstat.repositories.CityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getOneCity(int id) {
        return cityRepository.findByCityId(id);
    }

    public City getCityByName(String name) {
        return cityRepository.findByCityName(name);
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }
    
    public void saveAllCities(List<City> cities){
        cityRepository.saveAll(cities);
    }

    public void deleteCity(int id) {
        cityRepository.deleteById(id);
    }

}
