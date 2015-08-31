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
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.ejb.socialnw.annotaions.PrincipalUser;
import com.ejb.socialnw.entity.Role;
import com.ejb.socialnw.entity.User;
import com.ejb.socialnw.entity.location.City;
import com.ejb.socialnw.entity.location.Country;
import com.ejb.socialnw.service.UserService;
import com.ejb.socialnw.util.DateUtility;
import com.ejb.socialnw.util.LazyUserDataModel;


/**
 * User Controller class allows to do CRUD operations
 * with user and it's settings.
 * 
 * @author Andrei Bykov
 */

@ManagedBean
@ViewScoped
public class UserMB implements Serializable  {
	private static final long serialVersionUID = -3406076154361821847L;
	
	@Inject	private transient Logger logger;
	
	//User service for CRUD operation
	private @Inject UserService userServ;
	
    // Selected users that will be removed 
    private User[] selectedUsers; 
    
    // Lazy loading user list
    private LazyDataModel<User> lazyModel; 
    
    //This user will be changed on user's setting
  	private User userToChange;
  	
    // Selected user that will be updated
    private User selectedUser = new User();
    
    // Available role list
    private List<Role> roleList;
    
    //Authorized user
	private @Inject	@PrincipalUser User userPrincipal;
	
	//binding with jsf upload file to set user's avatar
	private UploadedFile file;
	
	//List of available countries
	private List<Country> countries; 
	
	//List of available cities
	private List<City> cities; 
	
	//SelectOneMeny country item
	private Country country;
	
    /**
     * Default constructor
     */
    public UserMB() {

    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role,country list for UserMB class
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init(){
        lazyModel = new LazyUserDataModel(userServ);
        roleList = userServ.findWithNamedQuery(Role.ALL);
        userToChange = userServ.find(userPrincipal.getId());
        countries = userServ.findWithNamedQuery(Country.ALL);
        logger.log(Level.INFO, "User controller initialized in #" + DateUtility.getCurrentDateTime());
    }

    /**
     * Create, Update and Delete operations
     */
        
    /**
     * Update all users parameters
     * @param actionEvent
     */
    public void doUpdateUser(){
    	userServ.update(userToChange);
    	FacesMessage msg = new FacesMessage("Settings will take effect after relogin;");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
    * Upload new image and set it on user avatar
    * @see UploadedFile
    * @param actionEvent
    */
   public void doUpdateUserAvatar(FileUploadEvent event){
	   try {
		    file = event.getFile();
			userToChange.getAvatar().setContentType(file.getContentType());
			userToChange.getAvatar().setFileName("filename");
			userToChange.getAvatar().setMedia(IOUtils.toByteArray(file.getInputstream()));
			doUpdateUser();
			file = null;
		} catch (IOException e) {
			
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Upload error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
   }
     
    /**
     * Delete selected users
     * @param actionEvent
     */
    public void doDeleteUsers(ActionEvent actionEvent){
            userServ.deleteItems(selectedUsers);
    }     
        
    /**
     * Getters, Setters
     */

    /**
     * @return selectedUser
     * @see User
     */
    public User getSelectedUser() {  
        return selectedUser;  
    }  

    /**
     *
     * @param selectedUser
     * @see User
     */
    public void setSelectedUser(User selectedUser) {  
            this.selectedUser = selectedUser;  
    } 
        
    /**
     *
     * @return selectedUsers
     * @see User
     */
    public User[] getSelectedUsers() {  
            return selectedUsers;  
    }  
        
    /**
     *
     * @param selectedUsers
     * @see User
     */
    public void setSelectedUsers(User[] selectedUsers) {  
            this.selectedUsers = selectedUsers;  
    }

    /**
     *
     * @return LazyDataModel
     */
    public LazyDataModel<User> getLazyModel() {
            return lazyModel;
    }

    /**
     *
     * @return List<Role>
     * @see Role
     */
    public List<Role> getRoleList() {
            return roleList;
    }

    /**
     *
     * @param roleList
     */
    public void setRoleList(List<Role> roleList) {
            this.roleList = roleList;
    }

    /**
    *
    * @return User
    * @see User
    */
	public User getUserToChange() {
		return userToChange;
	}

	/**
    *
    * @param userToChange
    * @see User
    */
	public void setUserToChange(User userToChange) {
		this.userToChange = userToChange;
	}
	
	/**
    *
    * @return UploadFile
    * @see UploadedFile
    */
	public UploadedFile getFile() {
		return file;
	}

	/**
    *
    * @param file
    * @see UploadedFile
    */
	public void setFile(UploadedFile file) {
		this.file = file;
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
	 * @see Country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
    
    
}
                    