package com.ejb.socialnw.entity.location;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ejb.socialnw.entity.Address;
import com.ejb.socialnw.entity.BaseEntity;

/**
 *  Simple business object representing a city.
 *  
 * @author Andrei Bykov
 */
@Entity
@Table(name="city")
@NamedQueries({
    @NamedQuery(name = City.ALL, query = "SELECT u FROM City u "),
    @NamedQuery(name = City.FIND_BY_NAME,  query="SELECT c FROM City c WHERE c.cityName = :cityName"),
    @NamedQuery(name = City.FIND_BY_COUNTRY_NAME,  query="SELECT c FROM City c WHERE c.countryIn = :countryIn"),
    @NamedQuery(name = City.TOTAL, query = "SELECT COUNT(u) FROM City u")})
public class City extends BaseEntity {

	private static final long serialVersionUID = -1294646622358584912L;
	public final static String ALL = "City.populateCities";
    public final static String TOTAL = "City.countCitiesTotal";
    public final static String FIND_BY_COUNTRY_NAME = "City.findByCityName";
    public final static String FIND_BY_NAME = "City.findByName";
    
	@Column(name = "city_name")
	private String cityName;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "country_id")
	private Country countryIn;

	@OneToMany(mappedBy = "city")
	private List<Address> address;

	/**
	 * Default constructor
	 */
	public City() {
		super();
		countryIn = new Country();
	}
	
	
	/**
     * Getters, Setters
     */
	/**
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return country where city located
	 * @see Country
	 */
	public Country getCountryIn() {
		return countryIn;
	}

	/**
	 * @param countryIn
	 * @see Country
	 */
	public void setCountryIn(Country countryIn) {
		this.countryIn = countryIn;
	}

	/**
	 * @return address
	 * @see Address
	 */
	public List<Address> getAddress() {
		return address;
	}

	/**
	 * @param address
	 * @see Address
	 */
	public void setAddress(List<Address> address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return cityName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result	+ ((cityName == null) ? 0 : cityName.hashCode());
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
		City other = (City) obj;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		return true;
	}
	
}
