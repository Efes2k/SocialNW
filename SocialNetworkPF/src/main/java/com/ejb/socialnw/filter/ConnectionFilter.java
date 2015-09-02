package com.ejb.socialnw.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.UserTransaction;

/**
 * 
 * Filter to open/close transaction per request
 * 
 * @author Andrei Bykov
 */
@Deprecated
public class ConnectionFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Resource
    private UserTransaction utx;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
            utx.begin();
            chain.doFilter(request, response);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}

