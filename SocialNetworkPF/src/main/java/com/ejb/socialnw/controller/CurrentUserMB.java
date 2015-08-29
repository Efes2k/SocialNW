package com.ejb.socialnw.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import com.ejb.socialnw.annotaions.PrincipalUser;
import com.ejb.socialnw.entity.User;
import com.ejb.socialnw.service.UserService;

/**
 * Authorized user producer for injectable principalUser
 * 
 * @author Andrei Bykov
 */
@ManagedBean
@SessionScoped
public class CurrentUserMB implements Serializable {
	private static final long serialVersionUID = 2322801641689140670L;
	@Inject private UserService das;
	private User userPrincipal;

	/**
	  * @param injectionPoint
	  * @return userPrincipal current authorized user
	 */
	@Produces
	@PrincipalUser
	@Named("currentUser")
	@SuppressWarnings("unchecked")
	public User getCurrentUser() {
		Map<String, Object> param = new HashMap<String, Object>();
		FacesContext fcx = FacesContext.getCurrentInstance();
		if (fcx.getExternalContext().getUserPrincipal() != null) {
			param.put("username", FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
			List<User> results = das.findWithNamedQuery(User.FIND_BY_NAME, param);
			if (results.isEmpty())
				try {
					throw new ServletException();
				} catch (ServletException e) {
					e.printStackTrace();
				}
			else if (results.size() == 1)
				userPrincipal = (User) results.get(0);
		}
		return userPrincipal;
	}

}