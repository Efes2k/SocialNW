package com.ejb.socialnw.entity.location;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ejb.socialnw.entity.BaseEntity;

/**
 * Simple business object representing a country.
 * 
 * @author Andrei Bykov
 */
@Entity
@Table(name = "country")
@NamedQueries({
        @NamedQuery(name = Country.ALL, query = "SELECT c FROM Country c "),
        @NamedQuery(name = Country.FIND_BY_NAME, query = "SELECT c FROM Country c WHERE c.countryName = :countryName"),
        @NamedQuery(name = Country.TOTAL, query = "SELECT COUNT(c) FROM Country c") })
public class Country extends BaseEntity {

    public final static String ALL = "Country.populateCountries";
    public final static String TOTAL = "Country.countCountriesTotal";
    public final static String FIND_BY_NAME = "Country.findByName";
    private static final long serialVersionUID = 3136721652292713893L;

    @Column(name = "country_name")
    private String countryName;

    @OneToMany(mappedBy = "countryIn", fetch = FetchType.EAGER)
    private List<City> cities;

    /**
     * Constructor
     */
    public Country() {
        super();
    }

    /**
     * Getters, Setters
     */
    /**
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return cities
     * @see City
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * @param cities
     * @see City
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return countryName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Country other = (Country) obj;
        if (countryName == null) {
            if (other.countryName != null)
                return false;
        } else if (!countryName.equals(other.countryName))
            return false;
        return true;
    }

}
