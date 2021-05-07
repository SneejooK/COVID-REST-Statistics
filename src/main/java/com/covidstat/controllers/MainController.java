package com.covidstat.controllers;

import com.covidstat.entities.City;
import com.covidstat.entities.Country;
import com.covidstat.services.CountryService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics/covid/country/")
public class MainController {

    private final CountryService countryService;

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(CountryService countryService) {
        this.countryService = countryService;
    }

    //Countries
    @GetMapping
    public List<Country> listCountries() {
        LOG.info("List all countries");
        return countryService.getAllCountries();
    }

    @GetMapping("{countryid}")
    public Country getCountry(@PathVariable int countryid) {
        LOG.info("Get one Country by id - " + countryid);
        return countryService.getOneCountry(countryid);
    }

    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        LOG.info("Create new country ");
        return countryService.saveCountry(country);
    }

    @PutMapping("{countryid}")
    public Country updateCountry(@PathVariable int countryid,
            @RequestBody Country country) {
        LOG.info("Update country");
        country.setCountryId(countryid);
        LOG.debug("Check for match with old id");
        return countryService.saveCountry(country);
    }

    @DeleteMapping("{countryid}")
    public void deleteCountry(@PathVariable int countryid) {
        LOG.info("Delete country by id - " + countryid);
        countryService.deleteCountry(countryid);
    }

    //Sities
    @GetMapping("{countryid}/city")
    public Set<City> listCitiesofTheCountry(@PathVariable int countryid) {
        LOG.info("List all cities of the country");
        return countryService.getOneCountry(countryid).getCities();
    }

    @GetMapping("{countryid}/city/{cityid}")
    public City getOneCityoftheCountry(@PathVariable int countryid,
            @PathVariable int cityid) {
        LOG.info("Get one City of the country by id");
        return countryService.getOneCountry(countryid).getCities().stream()
                .filter(city -> city.getCityId() == cityid).findFirst().orElse(null);

    }
}
