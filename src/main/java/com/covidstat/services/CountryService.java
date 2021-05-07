package com.covidstat.services;

import com.covidstat.entities.Country;
import com.covidstat.repositories.CountryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getOneCountry(int id) {
        return countryRepository.findByCountryId(id);
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public void saveAllCountries(List<Country> countries) {
        countryRepository.saveAll(countries);
    }

    public void deleteCountry(int id) {
        countryRepository.deleteById(id);
    }
}
