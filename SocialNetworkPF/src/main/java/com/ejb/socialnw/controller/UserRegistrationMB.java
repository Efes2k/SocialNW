package com.ejb.socialnw.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.ejb.socialnw.entity.Role;
import com.ejb.socialnw.entity.User;
import com.ejb.socialnw.entity.location.City;
import com.ejb.socialnw.entity.location.Country;
import com.ejb.socialnw.service.UserService;
import com.ejb.socialnw.util.DateUtility;
/**
 * User Controller class allows users to registration
 * @author Andrei Bykov
 */
@ManagedBean
@ViewScoped
public class UserRegistrationMB implements Serializable {
	private static final long serialVersionUID = 7215739521578860445L;
	
	@Inject	private transient Logger logger;
	
	//User service for CRUD operation
	@Inject private UserService userServ;
	
	// Creating new user
	private User newUser = new User();
	
	//List of available countries
    private List<Country> countries;
    
    //List of available cities
    private List<City> cities; 
    
    //SelectOneMeny country item
    private Country country;

    /**
     * Initializing countries for selection on registration page
     */
    @SuppressWarnings("unchecked")
	@PostConstruct
    public void init() {
        countries = userServ.findWithNamedQuery(Country.ALL);
        logger.log(Level.INFO, "UserWizard controller initialized in #" + DateUtility.getCurrentDateTime());
    }
    
    /**
     * Save new user in database
     */
    public void doCreateUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    	newUser.setRoles(getUserRoles());
    	userServ.update(newUser);
    	context.addMessage("success", new FacesMessage("Registration complete successfully"));
        context.getExternalContext().getFlash().setKeepMessages(true);
        //if user not authorized, redirect to login page
    	if(request.getUserPrincipal() == null){
	        try {
	        	context.getExternalContext().redirect(request.getContextPath() + "/Login.xhtml");
			} catch (IOException e) {
				logger.log(Level.WARNING, "IOException while save new user in #" + DateUtility.getCurrentDateTime());
			}
    	}
    	
    }
      
//    
//    public void checkDuplicateLogin(){
//    	 FacesContext context = FacesContext.getCurrentInstance();
//    	 Map<String, Object> map = new HashMap<String, Object>();
//    	 map.put("username", newUser.getUsername());
//    	 System.out.println("MAP    " + map);
//    	 if(userServ.findWithNamedQuery(User.FIND_BY_NAME,map).size() != 0){
//    		 context.addMessage("form:Username", new FacesMessage(FacesMessage.SEVERITY_ERROR, "User already exist", "User already exist"));
//    		 context.getExternalContext().getFlash().setKeepMessages(true);
//    		 System.out.println("FKIN EXIST");
//    	 }
//    }
    
    /**
     * Set new cities, if country was changed
     */
    public void changeCountry() {
    	if(country != null){
    	cities = country.getCities();
    	}
    }

    /**
     * List of user roles
     * @return
     * @deprecated
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    private List<Role> getUserRoles(){
    	return userServ.findWithNamedQuery(Role.LIST_NAMED);
    }
    
    
    /**
     * Getters, Setters
     */
    
    /**
     * @return user
     * @see User
     */
	public User getNewUser() {
		return newUser;
	}
	   /**
     * @param user
     * @see User
     */
	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	/**
	 * @return countries
	 * @see Country
	 */
	public List<Country> getCountries() {
		return countries;
	}

	/**
	 * @param countries
	 * @see Country
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
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

	/**
	 * @return country
	 * @see Country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country
	 *  @see Country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}


    
}