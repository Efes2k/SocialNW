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
 * To prevent user from going back to Login page if the user already logged in
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


















//package com.ejb.socialnw.filter;
//
//import java.io.IOException;
//import java.util.logging.Logger;
//
//import javax.annotation.Resource;
//import javax.inject.Inject;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.transaction.UserTransaction;
//
//public class ConnectionFilter implements Filter{  
//  
//	@Resource private UserTransaction utx;
//	@Inject	private transient Logger logger; 
//  
//    @Override  
//    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {  
//        System.out.println("Session filter start");  
//        try {  
//        	utx.begin();
//			chain.doFilter(request, response);
//			utx.commit();
//        } catch (Throwable e) {  
//            try {  
//                if(utx.isActive()){  
//                	utx.rollback();  
//                }  
//            } catch (Throwable t) {  
//                System.out.println("Exception in seesion filter");  
//                t.printStackTrace();  
//            }   
//              
//        }
//          
//    }  
//  
//    @Override  
//    public void init(FilterConfig arg0) throws ServletException {  
//    }
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//		
//	}  
//}  


//
//
//
//
//
//
//
//package com.ejb.socialnw.filter;
//
//import java.io.IOException;
//import java.util.logging.Logger;
//
//import javax.annotation.Resource;
//import javax.inject.Inject;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.transaction.UserTransaction;
//
///**
// *  Open Session in View Filter that will leave a
// *  database connection opened until the end of the user request. 
// *  
// * @author Andrei Bykov
// */
//
//public class ConnectionFilter implements Filter{  
//  
//	@Resource private UserTransaction utx;
//	@Inject	private transient Logger logger;
//
//  
//    @Override  
//    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {  
//    	try {
//    		   
//    		   chain.doFilter(request, response);
//    		   utx.commit();
//    		  } catch (Exception e) {
//    		   e.printStackTrace();
//    		  }
//          
//    }  
//  
//    @Override  
//    public void init(FilterConfig arg0) throws ServletException {  
//    }
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//		
//	}  
//}  
//
//
















































//package com.ejb.socialnw.filter;
//
//import java.io.IOException;
//
//import javax.annotation.Resource;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.transaction.UserTransaction;
//
//
///**
// * To prevent user from going back to Login page if the user already logged in
// * @author Andrei Bykov
// */
//@Deprecated
//public class ConnectionFilter implements Filter {
//
//	@Override
//	public void destroy() {
//
//	}
//
//	@Resource
//	private UserTransaction utx;
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws IOException, ServletException {
//		try {
//			utx.begin();
//			chain.doFilter(request, response);
//			utx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//
//	}
//}