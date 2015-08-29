package com.ejb.socialnw.controller;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ejb.socialnw.util.DateUtility;

/**
 * Login Controller class allows only authenticated users to log in to the web
 * application.
 *
 * @author Andrei Bykov
 */
@ManagedBean
@ViewScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 2925667878806624698L;
	@Inject	private transient Logger logger;
    private String username;
    private String password;

    /**
     * Default constructor
     */
    public LoginMB() {
    }

    /**
     * Listen for button clicks on the #{loginMB.login} action,
     * validates the username and password entered by the user and navigates to
     * the appropriate page.
     *
     * @param actionEvent
     */
    public void login(ActionEvent actionEvent) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            String navigateString = "";
            // Checks if username and password are valid if not throws a ServletException
            request.login(username, password);
            Principal principal = request.getUserPrincipal();
            if (request.isUserInRole("Administrator")) {
                navigateString = "/admin/AdminHome.xhtml";
            } else if (request.isUserInRole("Manager")) {
                navigateString = "/manager/ManagerHome.xhtml";
            } else if (request.isUserInRole("User")) {
                navigateString = "/user/feed/" + request.getUserPrincipal().getName();
            }
            try {
            	logger.log(Level.INFO, "User ({0}) loging in #" + DateUtility.getCurrentDateTime(), request.getUserPrincipal().getName());
                context.getExternalContext().redirect(request.getContextPath() + navigateString);
            } catch (IOException ex) {
            	logger.log(Level.SEVERE, "IOException, Login Controller" + "Username : " + principal.getName(), ex);
                context.addMessage(null, new FacesMessage("Error!", "Exception occured"));
            }
        } catch (ServletException e) {
        	logger.log(Level.INFO, "User loging failed in #" + DateUtility.getCurrentDateTime());
            context.addMessage(null, new FacesMessage("Error!", "The username or password you provided does not match our records."));
        }
    }

    /**
     * Listen for logout button clicks on the #{loginMB.logout} action
     * and navigates to login screen.
     */
    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        logger.log(Level.INFO, "User ({0}) loging out in #" + DateUtility.getCurrentDateTime() + request.getUserPrincipal().getName());
        if (session != null) {
            session.invalidate();
        }
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/Login.xhtml?faces-redirect=true");
    }
    
    
    /**
     * Getters, Setters
     */
    
    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
