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

//public class ConnectionFilter implements Filter {
//    @Inject
//    private transient Logger logger;
//    
//    
//    @Override
//    public void destroy() {
//
//    }
//
//    @Resource
//    private UserTransaction utx;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//            FilterChain chain) throws IOException, ServletException {
//        try {  
//            logger.info("Starting a database transaction");  
//            utx.begin();
//  
//            // Call the next filter (continue request processing)  
//            chain.doFilter(request, response);  
//  
//            // Commit and cleanup  
//            logger.info("Committing the database transaction");  
//            utx.commit();  
//  
//        } catch (Throwable ex) {  
//            // Rollback only  
//            ex.printStackTrace();  
//            try {  
//                
//                if (utx.getStatus() == 0) {  
//                    logger.info("Trying to rollback database transaction after exception");  
//                    utx.rollback();  
//                }  
//            } catch (Throwable rbEx) {  
//                logger.warning("Could not rollback transaction after exception!" + rbEx);
//            }  
//  
//            // Let others handle it... maybe another interceptor for exceptions?  
//            throw new ServletException(ex);  
//        }  
//
//    }
//
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {
//
//    }
//}

