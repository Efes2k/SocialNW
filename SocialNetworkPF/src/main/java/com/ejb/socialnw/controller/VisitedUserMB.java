package com.ejb.socialnw.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ejb.socialnw.annotaions.VisitedUser;
import com.ejb.socialnw.entity.User;
import com.ejb.socialnw.service.UserService;
import com.ejb.socialnw.util.DateUtility;


/**
 * Visited user producer for injectable visitedlUser
 * 
 * @author Andrei Bykov
 */

@Named
@ViewScoped
public class VisitedUserMB implements Serializable {
	private static final long serialVersionUID = 2322801641689140670L;
	@Inject private UserService userServ;
	@Inject	private transient Logger logger;
	private User visitedUser;
	private String userId;

	/**
	  * @param injectionPoint
	  * @return userVisited current visited user
	 */
	
	@Produces
	@VisitedUser
	@Named("visitedUser")
	@SuppressWarnings("unchecked")
	public User loadVisitedUser() {
		logger.log(Level.INFO, "Visited user construct, id = " + userId + "  " + DateUtility.getCurrentDateTime());
		if(userId != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username", userId);
			List<User> list = userServ.findWithNamedQuery(User.FIND_BY_NAME, params);
			if(list.size() == 1) {
				visitedUser = list.get(0);
			}
			else visitedUser = null;
		
		}
		return visitedUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}