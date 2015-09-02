package com.ejb.socialnw.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * To prevent user from going back to Login page if the user already logged in
 * 
 * @author Andrei Bykov
 */
public class LoginPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getUserPrincipal() != null) { // If user is already
                                                  // authenticated
            String navigateString = "";
            if (request.isUserInRole("Administrator")) {
                navigateString = "/admin/feed/" + request.getUserPrincipal().getName();
            } else if (request.isUserInRole("Manager")) {
                navigateString = "/manager/feed/" + request.getUserPrincipal().getName();
            } else if (request.isUserInRole("User")) {
                navigateString = "/user/feed/" + request.getUserPrincipal().getName();
            }
            System.out.println("Navigate String = " + navigateString);
            response.sendRedirect(request.getContextPath() + navigateString);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}