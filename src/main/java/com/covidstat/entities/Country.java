package com.covidstat.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Country implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;
    
    private String countryName;
    private int population;
    private int confirmed;
    private int deaths;
    
    @JsonManagedReference
    @OneToMany(targetEntity = City.class, mappedBy = "country",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<City> cities = new HashSet<>();
    
    public int getCountryId() {
        return countryId;
    }
    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public int getPopulation() {
        return population;
    }
    
    public void setPopulation(int population) {
        this.population = population;
    }
    
    public int getConfirmed() {
        return confirmed;
    }
    
    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
    
    public int getDeaths() {
        return deaths;
    }
    
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.countryId;
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Country other = (Country) obj;
        if (this.countryId != other.countryId) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Country{" + "countryId=" + countryId + ", countryName=" + countryName + ", population=" + population + ", confirmed=" + confirmed + ", deaths=" + deaths + '}';
    }
    
}
