package com.ejb.socialnw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ejb.socialnw.entity.location.City;

/**
 * Simple business object representing address.
 * 
 * @author Andrei Bykov
 */
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    private static final long serialVersionUID = -6378305029621790241L;

    @Column(length = 50)
    private String street;

    @Column(length = 50)
    private String suburb;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    /**
     * Default constructor
     */
    public Address() {
        city = new City();
    }

    /**
     * Getters, Setters
     */
    /**
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return suburb
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * @param suburb
     */
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    /**
     * @return city, where is user registered
     * @see City
     */
    public City getCity() {
        return city;
    }

    /**
     * Set city, where is user registered
     * 
     * @param city
     */
    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((suburb == null) ? 0 : suburb.hashCode());
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
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (suburb == null) {
            if (other.suburb != null)
                return false;
        } else if (!suburb.equals(other.suburb))
            return false;
        return true;
    }

}