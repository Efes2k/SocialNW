package com.ejb.socialnw.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.primefaces.validate.ClientValidator;

import com.ejb.socialnw.entity.User;
import com.ejb.socialnw.service.UserService;


/**
 *
 * Custom email validator utility class
 *
 * @author Andrei Bykov
 */

@FacesValidator("com.ejb.socialnw.util.userExist")
public class UsernameExistValidator implements Validator, ClientValidator {
 
	@Inject private UserService userServ;
  
    public UsernameExistValidator() {
    }
 
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	if(value == null) {
            return;
        }
    	
    	
    	 Map<String, Object> map = new HashMap<String, Object>();
    	 map.put("username", value.toString());
    	 System.out.println("MAP    " + map);
    	 if(userServ.findWithNamedQuery(User.FIND_BY_NAME,map).size() != 0){
    		 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "User already exist", "User already exist"));
    	 }
    }
 
    public Map<String, Object> getMetadata() {
        return null;
    }
 
    public String getValidatorId() {
        return "custom.userExist";
    }
     
}