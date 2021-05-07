package com.covidstat.schedule;

import com.covidstat.entities.City;
import com.covidstat.entities.Country;
import com.covidstat.services.CityService;
import com.covidstat.services.CountryService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Updater implements Job {

    private static final String URL = "https://covid-api.mmediagroup.fr/v1/cases?country=";
    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    private CountryService countryService;
    private CityService cityService;

    @Autowired
    public Updater(CountryService countryService, CityService cityService) {
        this.countryService = countryService;
        this.cityService = cityService;
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        updateListOfCountry(countryService.getAllCountries());
    }

    private void updateListOfCountry(List<Country> countries) {
        LOG.info("Ckech each country for updates");
        LOG.info("Created a new list wtih update countries");
        List<Country> updateCountries = new ArrayList<>();
        for (int i = 0; i < countries.size(); i++) {
            String output = getUrlContent(URL + countries.get(i).getCountryName());

            if (!output.isEmpty()) {

                LOG.debug("Ccreated a new list of countries to update");
                JSONObject json = new JSONObject(output);
                List<City> cities = new ArrayList<>();

                for (String s : json.keySet()) {

                    JSONObject jsonObject = json.getJSONObject(s);

                    if (s.equals("All")) {
                        LOG.debug("Update country - " + s);
                        countries.get(i).setPopulation(jsonObject.getInt("population"));
                        countries.get(i).setConfirmed(jsonObject.getInt("confirmed"));
                        countries.get(i).setDeaths(jsonObject.getInt("deaths"));

                    } else {
                        LOG.debug("Update city - %s", s);
                        City city = new City(
                                s,
                                jsonObject.getInt("confirmed"),
                                jsonObject.getInt("deaths"),
                                countries.get(i)
                        );

                        for (City c : countries.get(i).getCities()) {
                            if (c.getCityName().equals(s)) {
                                city.setCityId(c.getCityId());
                            }
                        }
                        LOG.debug("Add the updated city to the list");
                        cities.add(city);
                    }
                }
                LOG.debug("Update all cities of " + countries.get(i).getCountryName());
                cityService.saveAllCities(cities);
            }
            LOG.info("Add the country"  + countries.get(i).getCountryName() + "to list of updated countries");
            updateCountries.add(countries.get(i));
        }
        LOG.debug("Update all countries");
        countryService.saveAllCountries(updateCountries);
    }

    private String getUrlContent(String urlAdress) {
        LOG.info("Connection to API");
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            LOG.debug("Open stream from API");
            BufferedReader bf
                    = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            LOG.debug("Write line by line to string");
            String lane;
            while ((lane = bf.readLine()) != null) {
                sb.append(lane + "\n");
            }
            LOG.debug("Close stream");
            bf.close();
        } catch (Exception e) {
            LOG.error("Connection lsot");
        }
        return sb.toString();

    }

}
